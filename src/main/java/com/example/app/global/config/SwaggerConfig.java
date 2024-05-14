package com.example.app.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Template API")
                        .description("Alarm Application API doc")
                        .contact(new Contact().name("ujinjeong55@gmail.com"))
                        .license(new License().name("MIT"))
                );
    }

    @Bean
    public GroupedOpenApi apiV1() {
        return GroupedOpenApi.builder()
                .group("api-version-1")
                .pathsToMatch("/**")
                .pathsToExclude("/test/**")
                .build();
    }
}
