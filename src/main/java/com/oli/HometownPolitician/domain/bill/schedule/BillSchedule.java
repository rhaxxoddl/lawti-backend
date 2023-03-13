package com.oli.HometownPolitician.domain.bill.schedule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.oli.HometownPolitician.domain.bill.entity.Bill;
import com.oli.HometownPolitician.domain.bill.enumeration.PlenaryResultType;
import com.oli.HometownPolitician.domain.bill.enumeration.ProposerKind;
import com.oli.HometownPolitician.domain.bill.repository.BillRepository;
import com.oli.HometownPolitician.domain.bill.responseEntity.openAssembly.searchBills.SearchBill;
import com.oli.HometownPolitician.domain.bill.responseEntity.openAssembly.searchBills.SearchBillsBody;
import com.oli.HometownPolitician.domain.bill.responseEntity.publicData.getBillInfoList.BillInfo;
import com.oli.HometownPolitician.domain.bill.responseEntity.publicData.getBillInfoList.GetBillInfoListResponseBody;
import com.oli.HometownPolitician.domain.committee.entity.Committee;
import com.oli.HometownPolitician.domain.committee.repository.CommitteeRepository;
import com.oli.HometownPolitician.global.error.NotFoundError;
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
    private final CommitteeRepository committeeRepository;
    private final int DATA_SIZE = 250;
    private final int SCHEDULE_CYCLE_TIME = 1000 * 60 * 60 * 8;

    //    TODO Bill을 동적으로 파싱할 수 있게 변경
    @Scheduled(fixedDelay = SCHEDULE_CYCLE_TIME)
    public void parseGetBillInfoList1() {
        WebClient client = webClientFactory.getPublicPortalBillInfoService2Client();
        WebClient.UriSpec<?> uriSpec = client.get();

        int resultSize = DATA_SIZE;
        for (int i = 1; resultSize == DATA_SIZE && i <= 40; i++) {
            System.out.println("Bill Page: " + i);
            WebClient.RequestHeadersSpec<?> headersSpec = getBillInfoListUriWithParameter(uriSpec, i, DATA_SIZE);
            ResponseEntity<String> responseResult = getResponse(headersSpec);
            if (responseResult.getStatusCode().is4xxClientError()) {
                System.err.println("[ERROR] API를 잘못된 형식으로 호출했습니다");
                continue;
            } else if (responseResult.getStatusCode().is5xxServerError()) {
                System.err.println("[ERROR] API 서버 오류");
                break;
            }
            ObjectMapper objectMapper = ObjectMapperProvider.getCustomObjectMapper().registerModule(new JavaTimeModule());
            GetBillInfoListResponseBody getBillInfoListResponseBody;
            try {
                getBillInfoListResponseBody = objectMapper.readValue(responseResult.getBody(), GetBillInfoListResponseBody.class);
            } catch (JsonMappingException e) {
                throw new RuntimeException(e);
            } catch (JsonProcessingException e) {
                System.err.println(e);
                continue;
            }
            if (getBillInfoListResponseBody == null)
                break;
            List<Bill> bills = extractBillsFromGetBillInfoListResponseBody(getBillInfoListResponseBody);
            if (bills == null || bills.isEmpty())
                break;
            resultSize = bills.size();
            reflectBill(bills);
        }
    }

    @Scheduled(fixedDelay = SCHEDULE_CYCLE_TIME)
    public void parseGetBillInfoList2() {
        WebClient client = webClientFactory.getPublicPortalBillInfoService2Client();
        WebClient.UriSpec<?> uriSpec = client.get();
        int resultSize = DATA_SIZE;
        for (int i = 41; resultSize == DATA_SIZE && i <= 80; i++) {
            System.out.println("Bill Page: " + i);
            WebClient.RequestHeadersSpec<?> headersSpec = getBillInfoListUriWithParameter(uriSpec, i, DATA_SIZE);
            ResponseEntity<String> responseResult = getResponse(headersSpec);
            if (responseResult.getStatusCode().is4xxClientError()) {
                System.err.println("[ERROR] API를 잘못된 형식으로 호출했습니다");
                continue;
            } else if (responseResult.getStatusCode().is5xxServerError()) {
                System.err.println("[ERROR] API 서버 오류");
                break;
            }
            ObjectMapper objectMapper = ObjectMapperProvider.getCustomObjectMapper().registerModule(new JavaTimeModule());
            GetBillInfoListResponseBody getBillInfoListResponseBody;
            try {
                getBillInfoListResponseBody = objectMapper.readValue(responseResult.getBody(), GetBillInfoListResponseBody.class);
            } catch (JsonMappingException e) {
                throw new RuntimeException(e);
            } catch (JsonProcessingException e) {
                System.err.println(e);
                continue;
            }
            if (getBillInfoListResponseBody == null)
                break;
            List<Bill> bills = extractBillsFromGetBillInfoListResponseBody(getBillInfoListResponseBody);
            if (bills == null || bills.isEmpty())
                break;
            resultSize = bills.size();
            reflectBill(bills);
        }
    }

    @Scheduled(fixedDelay = SCHEDULE_CYCLE_TIME)
    public void parseGetBillInfoList3() {
        WebClient client = webClientFactory.getPublicPortalBillInfoService2Client();
        WebClient.UriSpec<?> uriSpec = client.get();
        int resultSize = DATA_SIZE;
        for (int i = 81; resultSize == DATA_SIZE && i <= 120; i++) {
            System.out.println("Bill Page: " + i);
            WebClient.RequestHeadersSpec<?> headersSpec = getBillInfoListUriWithParameter(uriSpec, i, DATA_SIZE);
            ResponseEntity<String> responseResult = getResponse(headersSpec);
            if (responseResult.getStatusCode().is4xxClientError()) {
                System.err.println("[ERROR] API를 잘못된 형식으로 호출했습니다");
                continue;
            } else if (responseResult.getStatusCode().is5xxServerError()) {
                System.err.println("[ERROR] API 서버 오류");
                break;
            }
            ObjectMapper objectMapper = ObjectMapperProvider.getCustomObjectMapper().registerModule(new JavaTimeModule());
            GetBillInfoListResponseBody getBillInfoListResponseBody;
            try {
                getBillInfoListResponseBody = objectMapper.readValue(responseResult.getBody(), GetBillInfoListResponseBody.class);
            } catch (JsonMappingException e) {
                throw new RuntimeException(e);
            } catch (JsonProcessingException e) {
                System.err.println(e);
                continue;
            }
            if (getBillInfoListResponseBody == null)
                break;
            List<Bill> bills = extractBillsFromGetBillInfoListResponseBody(getBillInfoListResponseBody);
            if (bills == null || bills.isEmpty())
                break;
            resultSize = bills.size();
            reflectBill(bills);
        }
    }

    @Scheduled(fixedDelay = SCHEDULE_CYCLE_TIME)
    public void parseSearchBills() {
        WebClient client = webClientFactory.getOpenAssemblyClient();
        WebClient.UriSpec<?> uriSpec = client.get();
        int resultSize = DATA_SIZE;
        for (int i = 287; resultSize == DATA_SIZE && i <= 120; i++) {
            System.out.println("Bill Page: " + i);
            WebClient.RequestHeadersSpec<?> headersSpec = searchBillsUriWithParameter(uriSpec, i, DATA_SIZE);
            ResponseEntity<String> responseResult = getResponse(headersSpec);
            if (responseResult.getStatusCode().is4xxClientError()) {
                System.err.println("[ERROR] API를 잘못된 형식으로 호출했습니다");
                continue;
            } else if (responseResult.getStatusCode().is5xxServerError()) {
                System.err.println("[ERROR] API 서버 오류");
                break;
            }
            ObjectMapper objectMapper = ObjectMapperProvider.getCustomObjectMapper();
            SearchBillsBody searchBillsBody;
            try {
                searchBillsBody = objectMapper.readValue(responseResult.getBody(), SearchBillsBody.class);
            } catch (JsonMappingException e) {
                throw new RuntimeException(e);
            } catch (JsonProcessingException e) {
                System.err.println(e);
                continue;
            }
            if (searchBillsBody == null)
                break;
            List<Bill> bills = extractBillsFromSearchBillsBody(searchBillsBody);
            if (bills == null || bills.isEmpty())
                break;
            resultSize = bills.size();
            reflectBill(bills);
        }

    }

    private List<Bill> extractBillsFromGetBillInfoListResponseBody(GetBillInfoListResponseBody getBillInfoListResponseBody) {
        if (getBillInfoListResponseBody == null
                || getBillInfoListResponseBody.getResponse() == null
                || getBillInfoListResponseBody.getResponse().getBody() == null
                || getBillInfoListResponseBody.getResponse().getBody().getItems() == null)
            return null;
        List<BillInfo> billInfos = Arrays.asList(getBillInfoListResponseBody.getResponse().getBody().getItems().getItem());
        return billInfos.stream()
                .map(Bill::fromByBillInfo)
                .toList();
    }

    private List<Bill> extractBillsFromSearchBillsBody(SearchBillsBody searchBillsBody) {
        if (searchBillsBody.getSearchBill() == null
                || searchBillsBody.getSearchBill().getSearchBills() == null)
            return null;
        List<Committee> committees = committeeRepository.findAll();
        List<SearchBill> searchBills = Arrays.asList(searchBillsBody.getSearchBill().getSearchBills());
        return searchBills.stream()
                .filter(searchBill -> searchBill.getAge() == 21)
                .map(searchBill -> Bill.SearchBillsBuilder()
                        .billExternalId(searchBill.getBill_id())
                        .number(searchBill.getBill_no())
                        .title(searchBill.getBill_name())
                        .proposeDate(searchBill.getPropose_dt())
                        .committee(
                                committees.stream()
                                        .filter(
                                                committee -> committee
                                                        .getExternalCommitteeId()
                                                        .equals(searchBill.getCurr_committee_id())
                                        )
                                        .findFirst()
                                        .orElseThrow(() -> new NotFoundError("해당하는 Committee을 찾을 수 없습니다"))
                        )
                        .proposerKind(ProposerKind.valueOfLable(searchBill.getProposer_kind()))
                        .committeeDate(searchBill.getCommittee_dt())
                        .plenaryResult(PlenaryResultType.valueOfLable(searchBill.getProc_result_cd()))
                        .plenaryProcessingDate(searchBill.getProc_dt())
                        .proposeAssembly(searchBill.getAge())
                        .build())
                .toList();
    }

    private void reflectBill(List<Bill> bills) {
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
                updateBill(originBill.get(), bill);
                // TODO 위원회 추가할 때 noti 보내는 로직 추가
            }
        }
    }

    private void updateBill(Bill originBill, Bill updatebill) {
        if (originBill == null || updatebill == null)
            throw new IllegalArgumentException("originBill, updatebill에 null이 들어올 수 없습니다");
        if (updatebill.getCurrentStage() != null)
            originBill.updateCurrentStage(updatebill.getCurrentStage());
        if (updatebill.getProposerKind() != null && originBill.getProposerKind() == null)
            originBill.setProposerKind(updatebill.getProposerKind());
        if (updatebill.getProposeDate() != null && originBill.getProposeDate() == null)
            originBill.setProposeDate(updatebill.getProposeDate());
        if (updatebill.getCommitteeDate() != null && originBill.getCommitteeDate() == null)
            originBill.setCommitteeDate(updatebill.getCommitteeDate());
        if (updatebill.getNoticeEndDate() != null && originBill.getNoticeEndDate() == null)
            originBill.setNoticeEndDate(updatebill.getNoticeEndDate());
        if (updatebill.getSummary() != null && originBill.getSummary() == null)
            originBill.setSummary(updatebill.getSummary());
        if (updatebill.getPlenaryResult() != null && originBill.getPlenaryResult() == null)
            originBill.setPlenaryResult(updatebill.getPlenaryResult());
        if (updatebill.getPlenaryProcessingDate() != null && originBill.getPlenaryProcessingDate() == null)
            originBill.setPlenaryProcessingDate(updatebill.getPlenaryProcessingDate());

    }

    private ResponseEntity<String> getResponse(WebClient.RequestHeadersSpec<?> headersSpec) {
        return headersSpec
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.USER_AGENT, "oli")
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve()
                .toEntity(String.class)
                .block();
    }

    private WebClient.RequestHeadersSpec<?> getBillInfoListUriWithParameter(WebClient.UriSpec<?> uriSpec, int pageIndex, int size) {
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

    private WebClient.RequestHeadersSpec<?> searchBillsUriWithParameter(WebClient.UriSpec<?> uriSpec, int pageIndex, int size) {
        return uriSpec.uri(
                uriBuilder -> uriBuilder
                        .pathSegment("TVBPMBILL11")
                        .queryParam("KEY", openApiProperty.getKeys().openAssembly())
                        .queryParam("Type", "json")
                        .queryParam("pIndex", pageIndex)
                        .queryParam("pSize", size)
                        .build(true)
        );
    }
}
