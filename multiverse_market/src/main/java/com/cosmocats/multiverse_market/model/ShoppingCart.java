package com.cosmocats.multiverse_market.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

// 5. Демонстрація @Component
@Component
// 5. Демонстрація скоупу 'prototype'
// @Scope("prototype") каже Spring:
// "Створюй НОВИЙ екземпляр цього біна КОЖЕН РАЗ, коли його запитують (ін'єктують)".
@Scope("prototype")
public class ShoppingCart {

    private final LocalDateTime creationTime;

    public ShoppingCart() {
        this.creationTime = LocalDateTime.now();
        System.out.println("СТВОРЕНО НОВИЙ ShoppingCart @" + this.hashCode());
    }

    public String getCreationTime() {
        return creationTime.toString();
    }

    public String getHash() {
        return String.valueOf(this.hashCode());
    }
}
