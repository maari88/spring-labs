package com.cosmocats.multiverse_market.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Multiverse Market API")
                        .version("1.0")
                        .description("RESTful API для управління міжгалактичним ринком (Лабораторна робота).")
                        .contact(new Contact()
                                .name("Студент")
                                .email("student@university.edu")));
    }
}