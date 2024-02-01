package com.maps.coin.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@OpenAPIDefinition(
        info = @Info(
                title = "COIN 프로젝트 API 명세서",
                description = "COIN 프로젝트에 사용되는 API 명세서",
                version = "v1"
        )
)
@Configuration
public class SwaggerConfig {

    @Bean
    @Profile("!Prod")
    public OpenAPI openAPI() {
        return new OpenAPI();
    }
}