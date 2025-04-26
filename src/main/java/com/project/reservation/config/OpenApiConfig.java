package com.project.reservation.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    private static final String TITLE = "Reservation System API";
    private static final String VERSION = "1.0";
    private static final String DESCRIPTION = "Reservation and cancellation system API";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title(TITLE)
                        .version(VERSION)
                        .description(DESCRIPTION));
    }
}

