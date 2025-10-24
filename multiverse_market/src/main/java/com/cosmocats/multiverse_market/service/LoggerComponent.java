package com.cosmocats.multiverse_market.service;

import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class LoggerComponent {

    public void log(String message) {
        System.out.println("[LOG " + LocalDateTime.now() + "]: " + message);
    }
}
