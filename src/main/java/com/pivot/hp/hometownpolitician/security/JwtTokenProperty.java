package com.pivot.hp.hometownpolitician.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "security.jwt.token")
public class JwtTokenProperty {

    private final String issuer;

    private final String secretKey;

    private final Long expireLength;

}
