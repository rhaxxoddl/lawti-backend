package com.oli.HometownPolitician.domain.bill.schedule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.oli.HometownPolitician.domain.bill.entity.Bill;
import com.oli.HometownPolitician.domain.bill.repository.BillRepository;
import com.oli.HometownPolitician.domain.bill.responseEntity.publicData.getBillInfoList.BillInfo;
import com.oli.HometownPolitician.domain.bill.responseEntity.publicData.getBillInfoList.GetBillInfoListBody;
import com.oli.HometownPolitician.domain.bill.responseEntity.publicData.getBillInfoList.GetBillInfoListResponse;
import com.oli.HometownPolitician.domain.bill.responseEntity.publicData.getBillInfoList.GetBillInfoListResponseBody;
import com.oli.HometownPolitician.global.error.FailedError;
import com.oli.HometownPolitician.global.factory.WebClientFactory;
import com.oli.HometownPolitician.global.property.OpenApiProperty;
import com.oli.HometownPolitician.global.provider.ObjectMapperProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class BillSchedule {
    private final WebClientFactory webClientFactory;
    private final OpenApiProperty openApiProperty;
    private final BillRepository billRepository;
    private final int DATA_SIZE = 100;
    private final int SCHEDULE_CYCLE_TIME = 1000 * 60 * 60 * 8;

    @Scheduled(fixedDelay = SCHEDULE_CYCLE_TIME)
    public void parseGetBillInfoList() {
        WebClient client = webClientFactory.getPublicPortalBillInfoService2Client();
        WebClient.UriSpec<?> uriSpec = client.get();
        int resultSize = DATA_SIZE;
        for (int i = 1; resultSize == DATA_SIZE; i++) {
            WebClient.RequestHeadersSpec<?> headersSpec = getUriWithParameter(uriSpec, i, DATA_SIZE);
            ResponseEntity<String> responseResult = getResponse(headersSpec);
            if (responseResult.getStatusCode().is4xxClientError())
                throw new FailedError("API를 잘못된 형식으로 호출했습니다");
            else if (responseResult.getStatusCode().is5xxServerError()) {
                throw new FailedError("API 서버 오류");
            }
            ObjectMapper objectMapper = ObjectMapperProvider.getCustomObjectMapper().registerModule(new JavaTimeModule());
            GetBillInfoListResponseBody getBillInfoListResponseBody;
            try {
                getBillInfoListResponseBody = objectMapper.readValue(responseResult.getBody(), GetBillInfoListResponseBody.class);
            } catch (JsonMappingException e) {
                throw new RuntimeException(e);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            GetBillInfoListResponse getBillInfoListResponse = getBillInfoListResponseBody.getResponse();
            GetBillInfoListBody getBillInfoListBody = getBillInfoListResponse.getBody();
            List<BillInfo> billInfos = Arrays.asList(getBillInfoListBody.getItems().getItem());
            resultSize = billInfos.size();
            List<Bill> bills = billInfos.stream()
                    .map(Bill::from)
                    .toList();
            updateBill(bills);
        }
    }

    private void updateBill(List<Bill> bills) {
        List<String> billExternalIdList = bills.stream()
                .map(bill -> bill.getBillExternalId())
                .toList();
        List<Bill> originBills = billRepository.queryBillsByBillExternalIdList(billExternalIdList);
        for (Bill bill : bills) {
            Optional<Bill> originBill = originBills.stream()
                    .filter(originbill
                            -> originbill.getBillExternalId().equals(bill.getBillExternalId())
                    ).findFirst();
            if (originBill.isEmpty()) {
                billRepository.save(bill);
            } else {
                originBill.get().updateCurrentStage(bill.getCurrentStage());
                // TODO 위원회 추가할 때 noti 보내는 로직 추가
            }
        }
    }

    private ResponseEntity<String> getResponse(WebClient.RequestHeadersSpec<?> headersSpec) {
        return headersSpec
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve()
                .toEntity(String.class)
                .doOnError(
                        error -> {
                            throw new FailedError("OpenApi로 데이터를 받아오는데 실패했습니다");
                        }
                )
                .block();
    }

    private WebClient.RequestHeadersSpec<?> getUriWithParameter(WebClient.UriSpec<?> uriSpec, int pageIndex, int size) {
        return uriSpec.uri(
                uriBuilder -> uriBuilder
                        .pathSegment("getBillInfoList")
                        .queryParam("serviceKey", openApiProperty.getKeys().publicDataBill())
                        .queryParam("pageNo", pageIndex)
                        .queryParam("numOfRows", size)
                        .queryParam("ord", "A01")
                        .queryParam("start_ord", 21)
                        .queryParam("end_ord", 21)
                        .queryParam("gbn", "dae_num")
                        .queryParam("bill_kind_cd", "B04")
                        .build(true)
        );
    }
}
