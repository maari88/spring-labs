package com.cosmocats.multiverse_market.service;

import com.cosmocats.multiverse_market.model.Product;
import com.cosmocats.multiverse_market.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private LoggerComponent logger;

    private final ProductRepository productRepository;

    private ShippingService shippingService;

    private static final PolicyFactory SANITIZE_POLICY = Sanitizers.FORMATTING.and(Sanitizers.BLOCKS);

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

    public Product saveProduct(Product product) {
        if (product == null) {
            logger.log("Спроба зберегти null-продукт.");
            throw new IllegalArgumentException("Продукт не може бути null");
        }

        logger.log("Санітизація продукту: " + product.getName());

        String cleanName = SANITIZE_POLICY.sanitize(product.getName());
        String cleanDescription = SANITIZE_POLICY.sanitize(product.getDescription());

        product.setName(cleanName);
        product.setDescription(cleanDescription);
        // product.setKeywords(cleanKeywords);

        logger.log("Збереження продукту: " + product.getName());

        return productRepository.save(product);
    }

}

