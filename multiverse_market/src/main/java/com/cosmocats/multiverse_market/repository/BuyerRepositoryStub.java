package com.cosmocats.multiverse_market.repository;

import com.cosmocats.multiverse_market.model.Buyer;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class BuyerRepositoryStub implements BuyerRepository {
    private final List<Buyer> storage = new CopyOnWriteArrayList<>();

    @Override public List<Buyer> findAll() { return new ArrayList<>(storage); }
    @Override public Optional<Buyer> findById(String id) { return storage.stream().filter(b -> b.getId().equals(id)).findFirst(); }
    @Override public Buyer save(Buyer b) { findById(b.getId()).ifPresent(storage::remove); storage.add(b); return b; }
    @Override public void deleteById(String id) { storage.removeIf(b -> b.getId().equals(id)); }
}

