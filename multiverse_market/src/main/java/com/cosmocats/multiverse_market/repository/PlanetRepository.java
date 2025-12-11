package com.cosmocats.multiverse_market.repository;

import com.cosmocats.multiverse_market.model.Planet;
import java.util.List;
import java.util.Optional;

public interface PlanetRepository {
    List<Planet> findAll();
    Optional<Planet> findById(Long id);
    Planet save(Planet p);
    void deleteById(Long id);
}