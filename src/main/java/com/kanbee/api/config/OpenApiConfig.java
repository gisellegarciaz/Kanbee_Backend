package com.kanbee.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Kanbee API")
                        .version("1.0")
                        .description("Stop \"pollen\" around and start moving! Kanbee is a REST API" +
                                "for dynamic board management. Built with Java and Spring Boot, this hive is fully" +
                                "connected to Supabase. No \"bee-zy\" work, just pure productivity."));
    }
}