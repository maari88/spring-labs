package com.cosmocats.multiverse_market.repository;

import com.cosmocats.multiverse_market.model.Seller;
import java.util.List;
import java.util.Optional;

public interface SellerRepository {
    List<Seller> findAll();
    Optional<Seller> findById(Long id);
    Seller save(Seller s);
    void deleteById(Long id);
}