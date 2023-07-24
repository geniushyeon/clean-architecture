package com.geniushyeon.architecture.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("clean architecture project API")
                .version("v1")
                .description("clean architecture project API 문서");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
