package com.oli.HometownPolitician.domain.politician.schedule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oli.HometownPolitician.domain.politician.entity.Politician;
import com.oli.HometownPolitician.domain.politician.repository.PoliticianRepository;
import com.oli.HometownPolitician.domain.politician.responseEntity.CurrentPoliticians;
import com.oli.HometownPolitician.domain.politician.responseEntity.CurrentPoliticiansBody;
import com.oli.HometownPolitician.domain.politician.responseEntity.PoliticianInfo;
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
public class PoliticianSchedule {
    private final WebClientFactory webClientFactory;
    private final OpenApiProperty openApiProperty;
    private final PoliticianRepository politicianRepository;
    private final int DATA_SIZE = 100;
    private final int DAESU = 21;
    private final int SCHEDULE_CYCLE_TIME = 1000 * 60 * 60 * 8;

    @Scheduled(fixedDelay = SCHEDULE_CYCLE_TIME)
    public void parseCurrentStatusOfPoliticians() {
        WebClient politicianClient = webClientFactory.getOpenAssemblyClient();
        WebClient.UriSpec<?> uriSpec = politicianClient.get();
        int resultSize = DATA_SIZE;

        for (int i = 1; resultSize == DATA_SIZE; i++) {
            WebClient.RequestHeadersSpec<?> headersSpec = getUriWithParameter(uriSpec, i, DATA_SIZE, "json", DAESU);
            ResponseEntity<String> responseResult = getResponse(headersSpec);
            ObjectMapper objectMapper = ObjectMapperProvider.getCustomObjectMapper();
            CurrentPoliticiansBody currentPoliticiansBody;
            try {
                currentPoliticiansBody = objectMapper.readValue(responseResult.getBody(), CurrentPoliticiansBody.class);
            } catch (Exception error) {
                throw new FailedError("Json 데이터를 객체로 변환하는데 실패했습니다\n detail: " + error);
            }
            if (currentPoliticiansBody == null || currentPoliticiansBody.getCurrentPoliticiansList() == null)
                break;
            List<Politician> politicians = extractPoliticians(currentPoliticiansBody);
            if (politicians == null || politicians.isEmpty())
                break;
            resultSize = politicians.size();
            updatePolitician(politicians);
        }
    }

    private List<Politician> extractPoliticians(CurrentPoliticiansBody currentPoliticiansBody) {
        List<CurrentPoliticians> currentPoliticiansList = Arrays.asList(currentPoliticiansBody.getCurrentPoliticiansList());
        if (currentPoliticiansList.isEmpty())
            return null;
        Head head = currentPoliticiansList.get(0).getHead()[1];
        if (head.getResult().getCode().equals("INFO-200"))
            throw new FailedError("Api로 데이터를 가져오는데 실패했습니다");
        if (currentPoliticiansList.get(1) == null || currentPoliticiansList.get(1).getPoliticianInfos() == null)
            return null;
        List<PoliticianInfo> politicianInfos = Arrays.asList(currentPoliticiansList.get(1).getPoliticianInfos());
        return politicianInfos.stream()
                .map(Politician::from)
                .toList();
    }

    private void updatePolitician(List<Politician> politicians) {
        for (Politician politician : politicians) {
            Optional<Politician> originPolitician = politicianRepository.qFindByPolitician(politician);
            if (originPolitician.isEmpty()) {
                politicianRepository.save(politician);
            } else {
                originPolitician.get().setParty(politician.getParty());
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
