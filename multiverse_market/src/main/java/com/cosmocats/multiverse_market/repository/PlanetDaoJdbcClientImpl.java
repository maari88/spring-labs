package com.cosmocats.multiverse_market.repository;

import com.cosmocats.multiverse_market.model.Planet;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class PlanetDaoJdbcClientImpl implements PlanetRepository {

    private final JdbcClient jdbcClient;

    public PlanetDaoJdbcClientImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    private Planet mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        return new Planet(
                rs.getLong("planet_id"),
                rs.getString("name"),
                rs.getString("region"),
                rs.getDouble("shipping_base_rate")
        );
    }

    @Override
    public List<Planet> findAll() {
        return jdbcClient.sql("SELECT * FROM planets")
                .query(this::mapRow)
                .list();
    }

    @Override
    public Optional<Planet> findById(Long id) {
        return jdbcClient.sql("SELECT * FROM planets WHERE planet_id = ?")
                .param(1, id)
                .query(this::mapRow)
                .optional();
    }

    @Override
    public Planet save(Planet planet) {
        if (planet.getId() == null) {
            // INSERT
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcClient.sql("INSERT INTO planets (name, region, shipping_base_rate) VALUES (?, ?, ?)")
                    .param(1, planet.getName())
                    .param(2, planet.getRegion())
                    .param(3, planet.getShippingBaseRate())
                    .update(keyHolder);

            if (keyHolder.getKey() != null) {
                planet.setId(keyHolder.getKey().longValue());
            }
            return planet;
        } else {
            // UPDATE
            jdbcClient.sql("UPDATE planets SET name = ?, region = ?, shipping_base_rate = ? WHERE planet_id = ?")
                    .param(1, planet.getName())
                    .param(2, planet.getRegion())
                    .param(3, planet.getShippingBaseRate())
                    .param(4, planet.getId())
                    .update();
            return planet;
        }
    }

    @Override
    public void deleteById(Long id) {
        jdbcClient.sql("DELETE FROM planets WHERE planet_id = ?")
                .param(1, id)
                .update();
    }
}
