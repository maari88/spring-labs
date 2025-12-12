package com.cosmocats.multiverse_market.model;

import jakarta.persistence.*;
import java.util.List;

@NamedQuery(
        name = "Product.findByPriceGreaterThan",
        query = "SELECT p FROM Product p WHERE p.price > :minPrice"
)
@Entity
@Table(name = "PRODUCTS")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prod_seq_gen")
    @SequenceGenerator(name = "prod_seq_gen", sequenceName = "PRODUCT_SEQ", allocationSize = 1)
    @Column(name = "product_id")
    private Long id;

    private String name;
    private String description;
    private double price;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToOne
    @JoinColumn(name = "planet_id")
    private Planet planet;

    private String keywords;

    public Product() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public Seller getSeller() { return seller; }
    public void setSeller(Seller seller) { this.seller = seller; }

    public Planet getPlanet() { return planet; }
    public void setPlanet(Planet planet) { this.planet = planet; }

    public String getKeywords() { return keywords; }
    public void setKeywords(String keywords) { this.keywords = keywords; }

    public Long getPlanetId() {
        return planet != null ? planet.getId() : null;
    }
    public void setPlanetId(Long planetId) {
        if (planetId != null) {
            this.planet = new Planet();
            this.planet.setId(planetId);
        }
    }


    public void setSellerId(Long sellerId) {
        if (sellerId != null) {
            this.seller = new Seller();
            this.seller.setId(sellerId);
        }
    }


    public Long getSellerId() {
        return seller != null ? seller.getId() : null;
    }
}
