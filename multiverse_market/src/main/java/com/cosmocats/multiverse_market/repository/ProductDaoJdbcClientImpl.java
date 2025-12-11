package com.cosmocats.multiverse_market.repository;

import com.cosmocats.multiverse_market.model.Product;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository("jdbcClientDao")
@Primary
public class ProductDaoJdbcClientImpl implements ProductDao {

    private final JdbcClient jdbcClient;

    public ProductDaoJdbcClientImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    private Product mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        String keywordsStr = rs.getString("keywords");
        List<String> keywords = keywordsStr != null ? Arrays.asList(keywordsStr.split(",")) : List.of();

        return new Product(
                rs.getLong("product_id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDouble("price"),
                rs.getLong("seller_id"),
                rs.getLong("planet_id"),
                keywords
        );
    }

    @Override
    public Product create(Product product) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcClient.sql("INSERT INTO products (name, description, price, seller_id, planet_id, keywords) VALUES (?, ?, ?, ?, ?, ?)")
                .param(1, product.getName())
                .param(2, product.getDescription())
                .param(3, product.getPrice())
                .param(4, product.getSellerId())
                .param(5, product.getPlanetId())
                .param(6, product.getKeywords() != null ? String.join(",", product.getKeywords()) : "")
                .update(keyHolder);

        if (keyHolder.getKey() != null) {
            product.setId(keyHolder.getKey().longValue());
        }
        return product;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return jdbcClient.sql("SELECT * FROM products WHERE product_id = ?")
                .param(1, id)
                .query(this::mapRow)
                .optional();
    }

    @Override
    public List<Product> findAll() {
        return jdbcClient.sql("SELECT * FROM products")
                .query(this::mapRow)
                .list();
    }

    @Override
    public int update(Product product) {
        return jdbcClient.sql("UPDATE products SET name=?, description=?, price=?, planet_id=?, keywords=? WHERE product_id=?")
                .param(1, product.getName())
                .param(2, product.getDescription())
                .param(3, product.getPrice())
                .param(4, product.getPlanetId())
                .param(5, product.getKeywords() != null ? String.join(",", product.getKeywords()) : "")
                .param(6, product.getId())
                .update();
    }

    @Override
    public int deleteById(Long id) {
        return jdbcClient.sql("DELETE FROM products WHERE product_id = ?")
                .param(1, id)
                .update();
    }

    @Override
    public List<Product> findByPlanetId(Long planetId) {
        return jdbcClient.sql("SELECT * FROM products WHERE planet_id = ?")
                .param(1, planetId)
                .query(this::mapRow)
                .list();
    }

    @Override
    public List<Product> searchByKeyword(String keyword) {
        String likeQuery = "%" + keyword + "%";
        return jdbcClient.sql("SELECT * FROM products WHERE name LIKE ? OR keywords LIKE ?")
                .param(1, likeQuery)
                .param(2, likeQuery)
                .query(this::mapRow)
                .list();
    }

    @Override
    public int updatePriceByPlanet(Long planetId, double multiplier) {
        return jdbcClient.sql("UPDATE products SET price = price * ? WHERE planet_id = ?")
                .param(1, multiplier)
                .param(2, planetId)
                .update();
    }
}