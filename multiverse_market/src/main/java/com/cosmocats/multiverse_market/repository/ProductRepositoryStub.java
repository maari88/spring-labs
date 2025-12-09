/*package com.cosmocats.multiverse_market.repository;

import com.cosmocats.multiverse_market.model.Product;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryStub implements ProductRepository {

    private final List<Product> storage = new CopyOnWriteArrayList<>();

    public ProductRepositoryStub() {
        Product p1 = new Product(
                "Марсіанський пісок (1кг)",
                "Справжній червоний пісок з кратеру Гусєв.",
                49.99,
                "seller-1",
                "planet-2",
                Arrays.asList("пісок", "марс", "сувенір")
        );
        p1.setId("product-1");
        storage.add(p1);

        Product p2 = new Product(
                "Ксено-кристал",
                "Рідкісний енергетичний кристал.",
                1299.00,
                "seller-2",
                "planet-3",
                Arrays.asList("кристал", "енергія", "ксенон")
        );
        p2.setId("product-2");
        storage.add(p2);
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(storage);
    }

    @Override
    public Optional<Product> findById(String id) {
        return storage.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    @Override
    public Product save(Product p) {
        if (p.getId() == null || p.getId().isEmpty()) {
            p.setId(UUID.randomUUID().toString());
            storage.add(p);
            return p;
        }

        Optional<Product> existing = findById(p.getId());
        if (existing.isPresent()) {
            storage.remove(existing.get());
            storage.add(p);
        } else {
            storage.add(p);
        }

        return p;
    }


    @Override
    public void deleteById(String id) {
        storage.removeIf(p -> p.getId().equals(id));
    }

    @Override
    public List<Product> findBySellerId(String sellerId) {
        return storage.stream().filter(p -> sellerId.equals(p.getSellerId())).collect(Collectors.toList());
    }

    @Override
    public List<Product> findByPlanetId(String planetId) {
        return storage.stream().filter(p -> planetId.equals(p.getPlanetId())).collect(Collectors.toList());
    }

    @Override
    public List<Product> searchByNameOrKeywords(String query) {
        String q = query == null ? "" : query.toLowerCase();
        return storage.stream().filter(p ->
                (p.getName() != null && p.getName().toLowerCase().contains(q))
                        || (p.getKeywords() != null && p.getKeywords().stream().anyMatch(k -> k.toLowerCase().contains(q)))
        ).collect(Collectors.toList());
    }
}
*/