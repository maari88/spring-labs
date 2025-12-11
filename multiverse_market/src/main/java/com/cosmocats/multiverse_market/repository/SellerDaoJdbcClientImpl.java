package com.cosmocats.multiverse_market.repository;

import com.cosmocats.multiverse_market.model.Seller;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class SellerDaoJdbcClientImpl implements SellerRepository {

    private final JdbcClient jdbcClient;

    public SellerDaoJdbcClientImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    private Seller mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        return new Seller(
                rs.getLong("seller_id"),
                rs.getString("name"),
                rs.getLong("planet_id"),
                rs.getString("contact_info")
        );
    }

    @Override
    public List<Seller> findAll() {
        return jdbcClient.sql("SELECT * FROM sellers")
                .query(this::mapRow)
                .list();
    }

    @Override
    public Optional<Seller> findById(Long id) {
        // Використовуємо ? замість :id
        return jdbcClient.sql("SELECT * FROM sellers WHERE seller_id = ?")
                .param(1, id) // 1-й параметр
                .query(this::mapRow)
                .optional();
    }

    @Override
    public Seller save(Seller seller) {
        if (seller.getId() == null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcClient.sql("INSERT INTO sellers (name, contact_info, planet_id) VALUES (?, ?, ?)")
                    .param(1, seller.getName())
                    .param(2, seller.getContact())
                    .param(3, seller.getPlanetId())
                    .update(keyHolder);

            if (keyHolder.getKey() != null) {
                seller.setId(keyHolder.getKey().longValue());
            }
            return seller;
        } else {
            jdbcClient.sql("UPDATE sellers SET name = ?, contact_info = ?, planet_id = ? WHERE seller_id = ?")
                    .param(1, seller.getName())
                    .param(2, seller.getContact())
                    .param(3, seller.getPlanetId())
                    .param(4, seller.getId())
                    .update();
            return seller;
        }
    }

    @Override
    public void deleteById(Long id) {
        jdbcClient.sql("DELETE FROM sellers WHERE seller_id = ?")
                .param(1, id)
                .update();
    }
}