package com.cosmocats.multiverse_market.repository;

import com.cosmocats.multiverse_market.model.Buyer;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class BuyerDaoJdbcClientImpl implements BuyerRepository {

    private final JdbcClient jdbcClient;

    public BuyerDaoJdbcClientImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    private Buyer mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        return new Buyer(
                rs.getLong("buyer_id"),
                rs.getString("username"),
                rs.getString("email")
        );
    }

    @Override
    public List<Buyer> findAll() {
        return jdbcClient.sql("SELECT * FROM buyers")
                .query(this::mapRow)
                .list();
    }

    @Override
    public Optional<Buyer> findById(Long id) {
        return jdbcClient.sql("SELECT * FROM buyers WHERE buyer_id = ?")
                .param(1, id)
                .query(this::mapRow)
                .optional();
    }

    @Override
    public Buyer save(Buyer buyer) {
        if (buyer.getId() == null) {
            // INSERT
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcClient.sql("INSERT INTO buyers (username, email) VALUES (?, ?)")
                    .param(1, buyer.getUsername())
                    .param(2, buyer.getEmail())
                    .update(keyHolder);

            if (keyHolder.getKey() != null) {
                buyer.setId(keyHolder.getKey().longValue());
            }
            return buyer;
        } else {
            // UPDATE
            jdbcClient.sql("UPDATE buyers SET username = ?, email = ? WHERE buyer_id = ?")
                    .param(1, buyer.getUsername())
                    .param(2, buyer.getEmail())
                    .param(3, buyer.getId())
                    .update();
            return buyer;
        }
    }

    @Override
    public void deleteById(Long id) {
        jdbcClient.sql("DELETE FROM buyers WHERE buyer_id = ?")
                .param(1, id)
                .update();
    }
}