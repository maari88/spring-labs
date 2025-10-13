package com.cosmocats.multiverse_market.repository;

import com.cosmocats.multiverse_market.model.Buyer;

import java.util.List;
import java.util.Optional;

public interface BuyerRepository {
    List<Buyer> findAll();
    Optional<Buyer> findById(String id);
    Buyer save(Buyer b);
    void deleteById(String id);
}

