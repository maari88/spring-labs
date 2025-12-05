package com.cosmocats.multiverse_market.service;

import com.cosmocats.multiverse_market.model.Product;
import com.cosmocats.multiverse_market.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    // --- 1. Отримати всі (для HTML сторінки) ---
    public List<Product> getAllProductsWithLogs() {
        logger.log("Запит списку всіх продуктів...");
        return productRepository.findAll();
    }

    // --- 2.2 Фільтрація та Пагінація (REST) ---
    public List<Product> findProducts(String planetId, Double minPrice, int page, int size) {
        logger.log(String.format("REST: Пошук з фільтром (planet=%s, price>=%s) сторінка %d", planetId, minPrice, page));

        return productRepository.findAll().stream()
                .filter(p -> planetId == null || p.getPlanetId().equals(planetId))
                .filter(p -> minPrice == null || p.getPrice() >= minPrice)
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    // --- 2.1 Пошук одного ---
    public Optional<Product> findById(String id) {
        return productRepository.findById(id);
    }

    // --- 2.1 Створення та Оновлення ---
    public Product saveProduct(Product product) {
        if (product == null) throw new IllegalArgumentException("Product cannot be null");

        if (product.getName() != null) product.setName(SANITIZE_POLICY.sanitize(product.getName()));
        if (product.getDescription() != null) product.setDescription(SANITIZE_POLICY.sanitize(product.getDescription()));

        return productRepository.save(product);
    }

    // --- 2.3 Часткове оновлення (PATCH) ---
    public Product updateProductPartially(String id, Map<String, Object> updates) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isEmpty()) return null;

        Product product = productOpt.get();

        if (updates.containsKey("name")) product.setName((String) updates.get("name"));
        if (updates.containsKey("description")) product.setDescription((String) updates.get("description"));
        if (updates.containsKey("price")) {
            Object price = updates.get("price");
            if (price instanceof Number) product.setPrice(((Number) price).doubleValue());
        }
        if (updates.containsKey("planetId")) product.setPlanetId((String) updates.get("planetId"));
        if (updates.containsKey("sellerId")) product.setSellerId((String) updates.get("sellerId"));

        return productRepository.save(product);
    }

    // --- 2.1 Видалення ---
    public void deleteById(String id) {
        productRepository.deleteById(id);
    }
}