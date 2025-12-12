package com.cosmocats.multiverse_market.service;

import com.cosmocats.multiverse_market.model.Product;
import com.cosmocats.multiverse_market.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final LoggerComponent logger;

    public ProductService(ProductRepository productRepository, LoggerComponent logger) {
        this.productRepository = productRepository;
        this.logger = logger;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findByPlanet(Long planetId) {
        return productRepository.findByPlanet_Id(planetId);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        if (product.getId() == null) {
            logger.log("Створення (JPA): " + product.getName());
        } else {
            logger.log("Оновлення (JPA): " + product.getId());
        }
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public void applyInflationToPlanet(Long planetId, double percentage, boolean simulateError) {
        double multiplier = 1.0 + (percentage / 100.0);
        logger.log("Транзакція. Інфляція " + percentage + "% на планеті " + planetId);

        List<Product> products = productRepository.findByPlanet_Id(planetId);

        for (Product p : products) {
            p.setPrice(p.getPrice() * multiplier);
        }
        productRepository.saveAll(products);

        if (simulateError) {
            throw new RuntimeException("Test rollback");
        }
        logger.log("Успішно оновлено " + products.size() + " товарів.");
    }
}