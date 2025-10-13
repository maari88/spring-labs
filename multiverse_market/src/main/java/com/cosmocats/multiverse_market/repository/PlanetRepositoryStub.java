package com.cosmocats.multiverse_market.repository;

import com.cosmocats.multiverse_market.model.Planet;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class PlanetRepositoryStub implements PlanetRepository {
    private final List<Planet> storage = new CopyOnWriteArrayList<>();

    @Override public List<Planet> findAll() { return new ArrayList<>(storage); }
    @Override public Optional<Planet> findById(String id) { return storage.stream().filter(p -> p.getId().equals(id)).findFirst(); }
    @Override public Planet save(Planet p) { findById(p.getId()).ifPresent(storage::remove); storage.add(p); return p; }
    @Override public void deleteById(String id) { storage.removeIf(p -> p.getId().equals(id)); }
}

