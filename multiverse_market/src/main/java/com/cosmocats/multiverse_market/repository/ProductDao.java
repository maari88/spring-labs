package com.cosmocats.multiverse_market.repository;

import com.cosmocats.multiverse_market.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductDao {
    Product create(Product product);
    Optional<Product> findById(Long id);
    List<Product> findAll();
    int update(Product product);
    int deleteById(Long id);

    List<Product> findByPlanetId(String planetId);
    List<Product> searchByKeyword(String keyword);

    int updatePriceByPlanet(String planetId, double multiplier);
}