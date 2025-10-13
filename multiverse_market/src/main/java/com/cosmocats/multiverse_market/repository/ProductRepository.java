package com.cosmocats.multiverse_market.repository;

import com.cosmocats.multiverse_market.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();
    Optional<Product> findById(String id);
    Product save(Product p);
    void deleteById(String id);

    List<Product> findBySellerId(String sellerId);
    List<Product> findByPlanetId(String planetId);
    List<Product> searchByNameOrKeywords(String query);
}

