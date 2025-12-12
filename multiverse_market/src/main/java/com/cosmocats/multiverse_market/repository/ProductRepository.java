package com.cosmocats.multiverse_market.repository;

import com.cosmocats.multiverse_market.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContaining(String partialName);

    List<Product> findByPlanet_Id(Long planetId);

    @Query("SELECT p FROM Product p WHERE p.description LIKE %:keyword% OR p.keywords LIKE %:keyword%")
    List<Product> searchByKeyword(@Param("keyword") String keyword);

    List<Product> findByPriceGreaterThan(@Param("minPrice") double minPrice);
}