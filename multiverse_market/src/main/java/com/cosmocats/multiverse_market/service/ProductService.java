package com.cosmocats.multiverse_market.service;

import com.cosmocats.multiverse_market.model.Product;
import com.cosmocats.multiverse_market.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private LoggerComponent logger;

    private final ProductRepository productRepository;

    private ShippingService shippingService;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    public void setShippingService(ShippingService shippingService) {
        this.shippingService = shippingService;
    }


    public List<Product> getAllProductsWithLogs() {
        logger.log("Запит списку всіх продуктів...");

        List<Product> products = productRepository.findAll();

        if (shippingService != null) {
            if (products.isEmpty()) {
                logger.log("Список продуктів порожній, вартість доставки не розраховується.");
            } else {

                logger.log("Розрахунок вартості доставки для " + products.size() + " продуктів...");

                for (Product product : products) {
                    String planetId = product.getPlanetId();
                    double cost = shippingService.getShippingCost(planetId);

                    logger.log("  - Вартість доставки для '" + product.getName() + "' складає: " + cost + " крд.");
                }
            }
        } else {
            logger.log("ShippingService не ін'єктовано.");
        }

        return products;
    }

}

