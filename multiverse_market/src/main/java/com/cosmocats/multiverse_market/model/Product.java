package com.cosmocats.multiverse_market.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public class Product {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    private String name;
    private String description;
    private double price;
    private String sellerId;
    private String planetId;
    private List<String> keywords;

    public Product() {}

    public Product(Long id, String name, String description, double price, String sellerId, String planetId, List<String> keywords) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.sellerId = sellerId;
        this.planetId = planetId;
        this.keywords = keywords;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getSellerId() { return sellerId; }
    public void setSellerId(String sellerId) { this.sellerId = sellerId; }

    public String getPlanetId() { return planetId; }
    public void setPlanetId(String planetId) { this.planetId = planetId; }

    public List<String> getKeywords() { return keywords; }
    public void setKeywords(List<String> keywords) { this.keywords = keywords; }
}