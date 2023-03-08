package com.oli.HometownPolitician.global.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "open-api")
public class OpenApiProperty {

    private final Timeout timeouts;

    private final Key keys;
    public record Timeout(int connect, int response, int read, int write) {
    }

    public record Key(String publicDataBill, String publicDataPolitician, String openAssembly) {
    }
}
