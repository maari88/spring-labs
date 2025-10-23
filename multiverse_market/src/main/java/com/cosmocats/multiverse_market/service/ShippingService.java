package com.cosmocats.multiverse_market.service;

import com.cosmocats.multiverse_market.repository.PlanetRepository;
import org.springframework.stereotype.Service;

// 5. Демонстрація @Service
// @Service - це спеціалізація @Component, яка позначає бізнес-логіку.
// Він також 'singleton' за замовчуванням.
@Service
public class ShippingService {

    private final PlanetRepository planetRepository;

    // Ми використовуємо ін'єкцію через конструктор для обов'язкових залежностей.
    public ShippingService(PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }

    public double getShippingCost(String planetId) {
        // Проста бізнес-логіка:
        // вартість = базова ставка планети * 1.1 (податок)
        return planetRepository.findById(planetId)
                .map(planet -> planet.getShippingBaseRate() * 1.1)
                .orElse(100.0); // Стандартна ставка, якщо планету не знайдено
    }
}
