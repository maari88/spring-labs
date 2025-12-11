package com.cosmocats.multiverse_market.repository;

import com.cosmocats.multiverse_market.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository("jdbcTemplateDao")
public class ProductDaoJdbcTemplateImpl implements ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public ProductDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Product> productRowMapper = (rs, rowNum) -> {
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
    };

    @Override
    public Product create(Product product) {
        String sql = "INSERT INTO products (name, description, price, seller_id, planet_id, keywords) VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());

            ps.setLong(4, product.getSellerId());
            ps.setLong(5, product.getPlanetId());

            ps.setString(6, product.getKeywords() != null ? String.join(",", product.getKeywords()) : "");
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            product.setId(keyHolder.getKey().longValue());
        }
        return product;
    }

    @Override
    public Optional<Product> findById(Long id) {
        String sql = "SELECT * FROM products WHERE product_id = ?";
        return jdbcTemplate.query(sql, productRowMapper, id).stream().findFirst();
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query("SELECT * FROM products", productRowMapper);
    }

    @Override
    public int update(Product product) {
        String sql = "UPDATE products SET name=?, description=?, price=?, planet_id=?, keywords=? WHERE product_id=?";
        return jdbcTemplate.update(sql,
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getPlanetId(),
                product.getKeywords() != null ? String.join(",", product.getKeywords()) : "",
                product.getId());
    }

    @Override
    public int deleteById(Long id) {
        // ✅ ВИПРАВЛЕНО: product_id замість id
        return jdbcTemplate.update("DELETE FROM products WHERE product_id = ?", id);
    }

    @Override
    public List<Product> findByPlanetId(Long planetId) { // ✅ ВИПРАВЛЕНО: String -> Long
        return jdbcTemplate.query("SELECT * FROM products WHERE planet_id = ?", productRowMapper, planetId);
    }

    @Override
    public List<Product> searchByKeyword(String keyword) {
        String likeQuery = "%" + keyword + "%";
        return jdbcTemplate.query("SELECT * FROM products WHERE name LIKE ? OR keywords LIKE ?", productRowMapper, likeQuery, likeQuery);
    }

    @Override
    public int updatePriceByPlanet(Long planetId, double multiplier) { // ✅ ВИПРАВЛЕНО: String -> Long
        return jdbcTemplate.update("UPDATE products SET price = price * ? WHERE planet_id = ?", multiplier, planetId);
    }
}