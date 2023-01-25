package com.oli.project.global.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "aws")
public class AwsProperty {

    private final Credentials credentials;

    private final S3 s3;

    private final String region;

    public record Credentials(String access, String secret) {
    }

    public record S3(String allowExtension, String bucket) {
    }

}