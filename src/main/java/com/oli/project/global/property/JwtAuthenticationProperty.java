package com.oli.project.global.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "security.jwt")
public class JwtAuthenticationProperty {

    private final String accessSecret;

    private final String refreshSecret;

    private final Web web;

    private final Application application;

    public record Web(Long accessExpireMinute, Long refreshExpireMinute) {

    }

    public record Application(Long accessExpireMinute, Long refreshExpireMinute) {

    }

}
