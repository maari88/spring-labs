package com.cosmocats.multiverse_market.repository;

import com.cosmocats.multiverse_market.model.Planet;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class PlanetRepositoryStub implements PlanetRepository {
    private final List<Planet> storage = new CopyOnWriteArrayList<>();

    public PlanetRepositoryStub() {
        Planet p1 = new Planet("Земля", "Сектор 001 (Сонячна система)", 15.99);
        p1.setId("planet-1");
        storage.add(p1);

        Planet p2 = new Planet("Марс", "Сектор 001 (Сонячна система)", 25.50);
        p2.setId("planet-2");
        storage.add(p2);

        Planet p3 = new Planet("Ксенон-Прайм", "Сектор 721 (Туманність Вуаль)", 150.75);
        p3.setId("planet-3");
        storage.add(p3);
    }

    @Override public List<Planet> findAll() { return new ArrayList<>(storage); }
    @Override public Optional<Planet> findById(String id) { return storage.stream().filter(p -> p.getId().equals(id)).findFirst(); }
    @Override public Planet save(Planet p) { findById(p.getId()).ifPresent(storage::remove); storage.add(p); return p; }
    @Override public void deleteById(String id) { storage.removeIf(p -> p.getId().equals(id)); }
}

