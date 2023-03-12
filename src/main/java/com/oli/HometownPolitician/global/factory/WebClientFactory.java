package com.oli.HometownPolitician.global.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
@Component
@RequiredArgsConstructor
public class WebClientFactory {
    private final HttpClientFactory httpClientFactory;

    public WebClient getPublicPortalBillInfoService2Client() {
        HttpClient httpClient = httpClientFactory.initHttpClient();
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl("http://apis.data.go.kr/9710000/BillInfoService2")
                .exchangeStrategies(defaultExchangeStrategies())
                .build();
    }

    public WebClient getOpenAssemblyClient() {
        HttpClient httpClient = httpClientFactory.initHttpClient();
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl("https://open.assembly.go.kr/portal/openapi")
                .build();
    }
    public ExchangeStrategies defaultExchangeStrategies() {

        return ExchangeStrategies.builder().codecs(config -> {
            config.defaultCodecs().maxInMemorySize(1024 * 1024); // max buffer 1MB 고정. default: 256 * 1024
            config.defaultCodecs().enableLoggingRequestDetails(true);
        }).build();
    }
}
