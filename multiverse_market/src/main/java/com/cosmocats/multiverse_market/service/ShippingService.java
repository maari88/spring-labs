package com.cosmocats.multiverse_market.service;

import com.cosmocats.multiverse_market.repository.PlanetRepository;
import org.springframework.stereotype.Service;

@Service
public class ShippingService {

    private final PlanetRepository planetRepository;

    public ShippingService(PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }

    public double getShippingCost(Long planetId) {
        if (planetId == null) return 100.0;

        return planetRepository.findById(planetId)
                .map(planet -> planet.getShippingBaseRate() * 1.1)
                .orElse(100.0);
    }
}