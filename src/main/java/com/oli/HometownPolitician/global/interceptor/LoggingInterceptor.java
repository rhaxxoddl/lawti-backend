package com.oli.HometownPolitician.global.interceptor;


import lombok.extern.slf4j.Slf4j;
import org.jboss.logging.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Configuration
public class LoggingInterceptor implements WebGraphQlInterceptor {

    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
        MDC.put("traceId", UUID.randomUUID().toString());
        log.info("request = {}", request);
        log.info("request getDocument = {}", request.getDocument());
        log.info("request getHeaders = {}", request.getHeaders());
        MDC.clear();
        Mono<WebGraphQlResponse> next = chain.next(request);
        return next;
    }

}