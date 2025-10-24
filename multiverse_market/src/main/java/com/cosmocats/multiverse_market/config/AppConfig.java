package com.cosmocats.multiverse_market.config;

import com.cosmocats.multiverse_market.service.WelcomeMessageGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public WelcomeMessageGenerator welcomeMessage() {
        return new WelcomeMessageGenerator("Ласкаво просимо на Ринок Мультивсесвіту!");
    }
}
