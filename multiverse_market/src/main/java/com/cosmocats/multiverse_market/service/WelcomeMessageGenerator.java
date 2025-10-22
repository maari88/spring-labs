package com.cosmocats.multiverse_market.service;

import java.time.LocalDateTime; // <--- ДОДАЙТЕ ЦЕЙ РЯДОК

// Це простий POJO (Plain Old Java Object).
// Spring не знає про цей клас, доки ми не оголосимо його в @Bean
public class WelcomeMessageGenerator {
    private final String message;

    public WelcomeMessageGenerator(String message) {
        this.message = message;
    }

    public String getWelcomeMessage() {
        // Тепер 'LocalDateTime' буде знайдено, і помилка зникне
        return message + " (Створено " + LocalDateTime.now() + ")";
    }
}
