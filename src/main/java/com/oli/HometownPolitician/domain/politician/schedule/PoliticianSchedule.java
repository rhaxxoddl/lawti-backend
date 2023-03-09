package com.oli.HometownPolitician.domain.politician.schedule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oli.HometownPolitician.domain.politician.entity.Politician;
import com.oli.HometownPolitician.domain.politician.repository.PoliticianRepository;
import com.oli.HometownPolitician.domain.politician.responseEntity.CurrentStatusOfPoliticians;
import com.oli.HometownPolitician.domain.politician.responseEntity.CurrentStatusOfPoliticiansBody;
import com.oli.HometownPolitician.domain.politician.responseEntity.PoliticianInfo;
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
public class PoliticianSchedule {
    private final WebClientFactory webClientFactory;
    private final OpenApiProperty openApiProperty;
    private final PoliticianRepository politicianRepository;
    private final int DATA_SIZE = 100;
    private final int DAESU = 21;
    private final int SCHEDULE_CYCLE_TIME = 1000 * 60 * 60 * 8;

    @Scheduled(fixedDelay = SCHEDULE_CYCLE_TIME)
    public void parseCurrentStatusOfPoliticians() {
        System.out.println("start parseCurrentStatusOfPoliticians");
        WebClient politicianClient = webClientFactory.getOpenAssemblyClient();
        WebClient.UriSpec<?> uriSpec = politicianClient.get();
        int resultSize = DATA_SIZE;

        for (int i = 1; resultSize == DATA_SIZE; i++) {
            WebClient.RequestHeadersSpec<?> headersSpec = getUriWithParameter(uriSpec, i, DATA_SIZE, "json", DAESU);
            ResponseEntity<String> responseResult = getResponse(headersSpec);
            ObjectMapper objectMapper = ObjectMapperProvider.getCustomObjectMapper();
            CurrentStatusOfPoliticiansBody currentStatusOfPoliticiansResult;
            try {
                currentStatusOfPoliticiansResult = objectMapper.readValue(responseResult.getBody(), CurrentStatusOfPoliticiansBody.class);
            } catch (Exception error) {
                throw new FailedError("Json 데이터를 객체로 변환하는데 실패했습니다\n detail: " + error);
            }

            List<CurrentStatusOfPoliticians> currentStatusOfPoliticiansList = Arrays.asList(currentStatusOfPoliticiansResult.getCurrentStatusOfPoliticiansList());
            List<PoliticianInfo> politicianInfos = Arrays.asList(currentStatusOfPoliticiansList.get(1).getPoliticians());
            resultSize = politicianInfos.size();
            List<Politician> politicians = politicianInfos.stream()
                    .map(Politician::from)
                    .toList();
            updatePolitician(politicians);
        }
    }

    private void updatePolitician(List<Politician> politicians) {
        for (Politician politician : politicians) {
            Optional<Politician> originPolitician = politicianRepository.queryPoliticiansByPolitician(politician);
            if (originPolitician.isEmpty()) {
                politicianRepository.save(politician);
            } else {
                originPolitician.get().setParty(politician.getParty());
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

    private WebClient.RequestHeadersSpec<?> getUriWithParameter(WebClient.UriSpec<?> uriSpec, int pageIndex, int size, String dataType, int daesu) {
        return uriSpec.uri(
                uriBuilder -> uriBuilder
                        .pathSegment("nprlapfmaufmqytet")
                        .queryParam("KEY", openApiProperty.getKeys().openAssembly())
                        .queryParam("pindex", pageIndex)
                        .queryParam("pSize", size)
                        .queryParam("Type", dataType)
                        .queryParam("DAESU", daesu)
                        .build()
        );
    }
}
