package com.cosmocats.multiverse_market.repository;

import com.cosmocats.multiverse_market.model.Seller;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class SellerRepositoryStub implements SellerRepository {
    private final List<Seller> storage = new CopyOnWriteArrayList<>();

    public SellerRepositoryStub() {
        Seller s1 = new Seller("Міжгалактичний Ілон", "planet-2", "elon@spacex.mars");
        s1.setId("seller-1");
        storage.add(s1);

        Seller s2 = new Seller("Корпорація 'Зірка'", "planet-3", "support@starc-corp.xen");
        s2.setId("seller-2");
        storage.add(s2);
    }

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

