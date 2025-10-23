package com.cosmocats.multiverse_market.service;

import com.cosmocats.multiverse_market.model.Product;
import com.cosmocats.multiverse_market.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// 2. Анотація @Service (частина завдання 5)
// Вказує, що це бін бізнес-логіки. За замовчуванням - singleton.
@Service
public class ProductService {

    // --- 1. Ін'єкція залежності (LoggerComponent) НАПРЯМУ У ПОЛЕ (@Autowired) ---
    // Spring "вставить" сюди бін типу LoggerComponent, коли знайде його.
    // Це найпростіший, але найменш рекомендований спосіб.
    @Autowired
    private LoggerComponent logger;

    // --- 2. Ін'єкція залежності (ProductRepository) ЧЕРЕЗ КОНСТРУКТОР ---
    // Це найкращий та рекомендований спосіб.
    // Spring автоматично знайде бін типу ProductRepository і передасть його сюди.
    private final ProductRepository productRepository;

    // --- 3. Ін'єкція залежності (ShippingService) ЧЕРЕЗ СЕТЕР ---
    // Використовується для опціональних залежностей.
    private ShippingService shippingService;

    // Конструктор для ін'єкції ProductRepository
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Сетер для ін'єкції ShippingService
    @Autowired
    public void setShippingService(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    /**
     * Отримує всі продукти та логує вартість доставки для КОЖНОГО.
     * (ОНОВЛЕНА ВЕРСІЯ)
     */
    public List<Product> getAllProductsWithLogs() {
        // Викликаємо наш Logger (Field Injection)
        logger.log("Запит списку всіх продуктів...");

        List<Product> products = productRepository.findAll();

        // Використовуємо наш ShippingService (Setter Injection)
        if (shippingService != null) {
            if (products.isEmpty()) {
                logger.log("Список продуктів порожній, вартість доставки не розраховується.");
            } else {

                // --- (ВИПРАВЛЕНО) ---
                // Тепер ми проходимо по КОЖНОМУ продукту
                logger.log("Розрахунок вартості доставки для " + products.size() + " продуктів...");

                for (Product product : products) {
                    String planetId = product.getPlanetId();
                    // Розраховуємо вартість для поточного продукту
                    double cost = shippingService.getShippingCost(planetId);

                    // Логуємо вартість для кожного
                    logger.log("  - Вартість доставки для '" + product.getName() + "' складає: " + cost + " крд.");
                }
                // ---
            }
        } else {
            logger.log("ShippingService не ін'єктовано.");
        }

        return products;
    }

    // Цей метод нам вже не потрібен, оскільки getAllProductsWithLogs() робить те ж саме
    // public List<Product> findAll() {
    //     return productRepository.findAll();
    // }
}

