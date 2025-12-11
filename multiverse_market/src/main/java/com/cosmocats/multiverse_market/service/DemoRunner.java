package com.cosmocats.multiverse_market.service;

import com.cosmocats.multiverse_market.model.ShoppingCart;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class DemoRunner implements CommandLineRunner {

    private final ApplicationContext applicationContext;
    private final JdbcTemplate jdbcTemplate;

    public DemoRunner(ApplicationContext applicationContext, JdbcTemplate jdbcTemplate) {
        this.applicationContext = applicationContext;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("=================================================");
        System.out.println("START OF SYSTEM CHECK");
        System.out.println("=================================================");

        // 1. Перевірка БД
        System.out.println("\n[1] CHECKING THE CONNECTION TO THE DATABASE (ORACLE)...");
        try {
            Integer result = jdbcTemplate.queryForObject("SELECT 1 FROM DUAL", Integer.class);

            System.out.println("   ✅ SUCCESS: Test query 'SELECT 1 FROM DUAL' executed. Result: " + result);

            DataSource dataSource = jdbcTemplate.getDataSource();
            if (dataSource != null) {
                try (Connection connection = dataSource.getConnection()) {
                    System.out.println("   ✅ SUCCESS: Connection established!");
                    System.out.println("   URL database: " + connection.getMetaData().getURL());
                    System.out.println("   User: " + connection.getMetaData().getUserName());
                }
            }
        } catch (Exception e) {
            System.err.println("   ❌ FAIL: Error connecting to the database!");
            System.err.println("   Error text: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n[2] Singleton vs Prototype:");
        LoggerComponent logger1 = applicationContext.getBean(LoggerComponent.class);
        LoggerComponent logger2 = applicationContext.getBean(LoggerComponent.class);
        System.out.println("   Singleton (LoggerComponent): logger1 == logger2? -> " + (logger1 == logger2));

        ShoppingCart cart1 = applicationContext.getBean(ShoppingCart.class);
        ShoppingCart cart2 = applicationContext.getBean(ShoppingCart.class);
        System.out.println("   Prototype (ShoppingCart): cart1 != cart2? -> " + (cart1 != cart2));

        System.out.println("=================================================");
    }
}