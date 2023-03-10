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
            ObjectMapper objectMapper = ObjectMapperProvider.getCustomObjectMapper();
            CurrentCommitteesBody currentCommitteesBody;
            try {
                currentCommitteesBody = objectMapper.readValue(responseResult.getBody(), CurrentCommitteesBody.class);
            } catch (Exception error) {
                throw new FailedError("Json 데이터를 객체로 변환하는데 실패했습니다\n detail: " + error);
            }
            List<CurrentCommittees> currentCommitteesList = Arrays.asList(currentCommitteesBody.getCurrentCommitteesList());
            Head head = currentCommitteesList.get(0).getHead()[1];
            if (head.getResult().getCode() == "INFO-200")
                throw new FailedError("Api로 데이터를 가져오는데 실패했습니다");
            List<CommitteeInfo> committeeInfos = Arrays.asList(currentCommitteesList.get(1).getCommitteeInfos());
            resultSize = committeeInfos.size();
            List<Committee> committees = committeeInfos.stream()
                    .map(Committee::from)
                    .toList();
            updateCommittee(committees);
        }
    }

    private void updateCommittee(List<Committee> committees) {
        for (Committee committee : committees) {
            Optional<Committee> originCommittee = committeeRepository.qFindByExternalCommitteeId(committee.getExternalCommitteeId());
            if (originCommittee.isEmpty()) {
                committeeRepository.save(committee);
            }
        }
    }

    private static ResponseEntity<String> getResponse(WebClient.RequestHeadersSpec<?> headersSpec) {
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
