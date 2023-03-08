package com.oli.HometownPolitician.global.factory;

import com.oli.HometownPolitician.global.property.OpenApiProperty;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class HttpClientFactory {
    private final OpenApiProperty.Timeout timeout;

    public HttpClient initHttpClient() {
        return HttpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeout.connectTimeout())
                .responseTimeout(Duration.ofMillis(timeout.responseTimeout()))
                .doOnConnected(connection ->
                        connection.addHandlerLast(new ReadTimeoutHandler(timeout.readTimeout()))
                                .addHandlerLast(new WriteTimeoutHandler(timeout.writeTimeout())));
    }

    public HttpClient customHttpClient(int connectTimeout, int responseTimeout, int readTimeout, int writeTimeout){
        return HttpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout)
                .responseTimeout(Duration.ofMillis(responseTimeout))
                .doOnConnected(connection ->
                        connection.addHandlerLast(new ReadTimeoutHandler(readTimeout))
                                .addHandlerLast(new WriteTimeoutHandler(writeTimeout)));
    }
}
