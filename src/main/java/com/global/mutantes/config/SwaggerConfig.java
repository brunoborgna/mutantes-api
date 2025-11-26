package com.global.mutantes.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Mutant Detector API - Global de Borgna Bruno - Legano N°50813 - 3K09 - 2025")
                        .version("1.0.0-FINAL")
                        .description("API REST para el reclutamiento de Magneto. Implementada con Spring Boot 3."));
    }
}