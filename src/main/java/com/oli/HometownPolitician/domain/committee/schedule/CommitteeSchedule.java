package com.oli.HometownPolitician.domain.committee.schedule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oli.HometownPolitician.domain.committee.entity.Committee;
import com.oli.HometownPolitician.domain.committee.repository.CommitteeRepository;
import com.oli.HometownPolitician.domain.committee.responseEntity.CommitteeInfo;
import com.oli.HometownPolitician.domain.committee.responseEntity.CurrentCommittees;
import com.oli.HometownPolitician.domain.committee.responseEntity.CurrentCommitteesBody;
import com.oli.HometownPolitician.global.error.FailedError;
import com.oli.HometownPolitician.global.factory.WebClientFactory;
import com.oli.HometownPolitician.global.property.OpenApiProperty;
import com.oli.HometownPolitician.global.provider.ObjectMapperProvider;
import com.oli.HometownPolitician.global.responseEntity.Head;
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
public class CommitteeSchedule {
    private final WebClientFactory webClientFactory;
    private final OpenApiProperty openApiProperty;
    private final CommitteeRepository committeeRepository;
    private final int DATA_SIZE = 50;

    private final int SCHEDULE_CYCLE_TIME = 1000 * 60 * 60 * 24;
    @Scheduled(fixedDelay = SCHEDULE_CYCLE_TIME)
    public void parseCurrentStatusOfCommittees() {
        WebClient client = webClientFactory.getOpenAssemblyClient();
        WebClient.UriSpec<?> uriSpec = client.get();
        int resultSize = DATA_SIZE;
        for (int i = 1; resultSize == DATA_SIZE; i++) {
            WebClient.RequestHeadersSpec<?> headersSpec = getUriWithParameter(uriSpec, i, DATA_SIZE, "json");
            ResponseEntity<String> responseResult = getResponse(headersSpec);
            if (responseResult.getStatusCode().is4xxClientError())
                throw new FailedError("API를 잘못된 형식으로 호출했습니다");
            else if (responseResult.getStatusCode().is5xxServerError()) {
                throw new FailedError("API 서버 오류");
            }
            ObjectMapper objectMapper = ObjectMapperProvider.getCustomObjectMapper();
            CurrentCommitteesBody currentCommitteesBody;
            try {
                currentCommitteesBody = objectMapper.readValue(responseResult.getBody(), CurrentCommitteesBody.class);
            } catch (Exception error) {
                throw new FailedError("Json 데이터를 객체로 변환하는데 실패했습니다\n detail: " + error);
            }
            if (currentCommitteesBody == null || currentCommitteesBody.getCurrentCommitteesList() == null)
                break;
            List<Committee> committees = extractCommittees(currentCommitteesBody);
            if (committees == null || committees.isEmpty())
                break;
            resultSize = committees.size();
            updateCommittee(committees);
        }
    }

    private List<Committee> extractCommittees(CurrentCommitteesBody currentCommitteesBody) {
        List<CurrentCommittees> currentCommitteesList = Arrays.asList(currentCommitteesBody.getCurrentCommitteesList());
        if (currentCommitteesList.isEmpty() || currentCommitteesList.get(0) == null || currentCommitteesList.get(0).getHead() == null)
            return null;
        Head head = currentCommitteesList.get(0).getHead()[1];
        if (head.getResult().getCode().equals("INFO-200"))
            throw new FailedError("Api로 데이터를 가져오는데 실패했습니다");
        if (currentCommitteesList.get(1) == null || currentCommitteesList.get(1).getCommitteeInfos() == null)
            return null;
        List<CommitteeInfo> committeeInfos = Arrays.asList(currentCommitteesList.get(1).getCommitteeInfos());
        return committeeInfos.stream()
                .map(Committee::from)
                .toList();
    }

    private void updateCommittee(List<Committee> committees) {
        for (Committee committee : committees) {
            Optional<Committee> originCommittee = committeeRepository.qFindByExternalCommitteeId(committee.getExternalCommitteeId());
            if (originCommittee.isEmpty()) {
                committeeRepository.save(committee);
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

    private WebClient.RequestHeadersSpec<?> getUriWithParameter(WebClient.UriSpec<?> uriSpec, int pageIndex, int size, String dataType) {
        return uriSpec.uri(
                uriBuilder -> uriBuilder
                        .pathSegment("nxrvzonlafugpqjuh")
                        .queryParam("KEY", openApiProperty.getKeys().openAssembly())
                        .queryParam("pindex", pageIndex)
                        .queryParam("pSize", size)
                        .queryParam("Type", dataType)
                        .build()
        );
    }
}
