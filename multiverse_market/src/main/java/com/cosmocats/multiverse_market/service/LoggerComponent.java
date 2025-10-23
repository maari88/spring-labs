package com.cosmocats.multiverse_market.service;

import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

// 5. Демонстрація @Component
// @Component - це базова анотація для будь-якого Spring-біна.
// За замовчуванням, він буде 'singleton' (один на всю програму).
@Component
public class LoggerComponent {

    public void log(String message) {
        System.out.println("[LOG " + LocalDateTime.now() + "]: " + message);
    }
}
