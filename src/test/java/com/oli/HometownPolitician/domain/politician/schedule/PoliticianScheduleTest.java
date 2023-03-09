package com.oli.HometownPolitician.domain.politician.schedule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oli.HometownPolitician.domain.politician.responseEntity.CurrentStatusOfPoliticiansResult;
import com.oli.HometownPolitician.global.factory.WebClientFactory;
import com.oli.HometownPolitician.global.property.OpenApiProperty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PoliticianScheduleTest {
    @Autowired
    private WebClientFactory webClientFactory;
    @Autowired
    private OpenApiProperty openApiProperty;

    @Test
    void parsePolitician() throws JsonProcessingException {
        System.out.println("==============start==============");
        WebClient politicianClient = webClientFactory.getOpenAssemblyClient();
        WebClient.UriSpec<?> uriSpec = politicianClient.get();

        WebClient.RequestHeadersSpec<?> headersSpec = uriSpec.uri(
                uriBuilder -> uriBuilder
                        .pathSegment("nprlapfmaufmqytet")
                        .queryParam("KEY", openApiProperty.getKeys().openAssembly())
                        .queryParam("pindex", 1)
                        .queryParam("pSize", 5)
                        .queryParam("Type", "json")
                        .queryParam("DAESU", 21)
                        .build()
        );
        ResponseEntity<String> responseMono = headersSpec
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.ALL_VALUE)
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve()
                .toEntity(String.class)
                .doOnSuccess(
                        result -> {
                            System.out.println(result.toString());
                        }
                )
                .doOnError(error -> {
                    System.out.println(error.toString());
                })
                .block();
        ObjectMapper objectMapper = new ObjectMapper();
        CurrentStatusOfPoliticiansResult jsonString = objectMapper.readValue(responseMono.getBody(), CurrentStatusOfPoliticiansResult.class);
        System.out.println(jsonString);
    }
}