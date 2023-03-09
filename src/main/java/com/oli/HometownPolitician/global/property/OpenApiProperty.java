package com.oli.HometownPolitician.global.property;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "open-api")
public class OpenApiProperty {
    private final Key keys;
    private final Timeout timeouts;

    public record Key(
            String publicDataBill,
            String publicDataPolitician,
            String openAssembly
    ) {}
    public record Timeout(
            int connect,
            int response,
            int read,
            int write
    ) {}
}
