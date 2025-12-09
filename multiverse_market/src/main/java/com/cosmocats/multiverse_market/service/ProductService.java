package com.cosmocats.multiverse_market.service;

import com.cosmocats.multiverse_market.model.Product;
import com.cosmocats.multiverse_market.repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductDao productDao;
    private final LoggerComponent logger;

    @Autowired
    public ProductService(@Qualifier("jdbcClientDao") ProductDao productDao, LoggerComponent logger) {
        this.productDao = productDao;
        this.logger = logger;
    }

    public List<Product> findAll() {
        return productDao.findAll();
    }

    public List<Product> findByPlanet(String planetId) {
        return productDao.findByPlanetId(planetId);
    }

    public Optional<Product> findById(Long id) {
        return productDao.findById(id);
    }

    public Product save(Product product) {
        if (product.getId() == null) {
            logger.log("Створення нового продукту: " + product.getName());
            return productDao.create(product);
        } else {
            logger.log("Оновлення продукту ID: " + product.getId());
            productDao.update(product);
            return product;
        }
    }

    public void deleteById(Long id) {
        productDao.deleteById(id);
    }

    @Transactional
    public void applyInflationToPlanet(String planetId, double percentage, boolean simulateError) {
        double multiplier = 1.0 + (percentage / 100.0);
        logger.log("Початок транзакції. Інфляція " + percentage + "% на планеті " + planetId);

        int updatedRows = productDao.updatePriceByPlanet(planetId, multiplier);
        logger.log("Оновлено записів: " + updatedRows);

        if (simulateError) {
            logger.log("Помилка! Відкат транзакції");
            throw new RuntimeException("Штучна помилка для тестування відкату транзакції.");
        }

        logger.log("Транзакція успішно завершена.");
    }
}