package com.oli.HometownPolitician.global.config;

import com.oli.HometownPolitician.global.property.OpenApiProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({OpenApiProperty.class})
@RequiredArgsConstructor
public class OpenApiConfig {
    private final OpenApiProperty property;
    public OpenApiProperty openApiProperty() {
        return this.property;
    }
}
