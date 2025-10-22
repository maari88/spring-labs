package com.cosmocats.multiverse_market.config;

import com.cosmocats.multiverse_market.service.WelcomeMessageGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // Позначає клас як джерело конфігурацій для бінів
public class AppConfig {

    // 5. Демонстрація @Bean
    // @Bean каже Spring: "Виконай цей метод і зареєструй
    // об'єкт, що повернувся, як бін в контексті".
    // За замовчуванням, це 'singleton'.
    @Bean
    public WelcomeMessageGenerator welcomeMessage() {
        // Ми можемо тут налаштувати об'єкт
        return new WelcomeMessageGenerator("Ласкаво просимо на Ринок Мультивсесвіту!");
    }
}
