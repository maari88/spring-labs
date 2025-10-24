package com.cosmocats.multiverse_market.service;

import java.time.LocalDateTime;

public class WelcomeMessageGenerator {
    private final String message;

    public WelcomeMessageGenerator(String message) {
        this.message = message;
    }

    public String getWelcomeMessage() {
        return message + " (Створено " + LocalDateTime.now() + ")";
    }
}
