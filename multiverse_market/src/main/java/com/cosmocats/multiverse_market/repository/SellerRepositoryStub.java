package com.cosmocats.multiverse_market.repository;

import com.cosmocats.multiverse_market.model.Seller;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class SellerRepositoryStub implements SellerRepository {
    private final List<Seller> storage = new CopyOnWriteArrayList<>();

    @Override
    public List<Seller> findAll() { return new ArrayList<>(storage); }

    @Override
    public Optional<Seller> findById(String id) {
        return storage.stream().filter(s -> s.getId().equals(id)).findFirst();
    }

    @Override
    public Seller save(Seller s) {
        findById(s.getId()).ifPresent(existing -> storage.remove(existing));
        storage.add(s);
        return s;
    }

    @Override
    public void deleteById(String id) {
        storage.removeIf(s -> s.getId().equals(id));
    }
}

