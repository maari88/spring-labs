package com.cosmocats.multiverse_market.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "PLANETS")
public class Planet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Oracle Identity column
    @Column(name = "planet_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    private String region;

    @Column(name = "shipping_base_rate")
    private double shippingBaseRate;


    public Planet() {}

    public Planet(String name, String region, double shippingBaseRate) {
        this.name = name; this.region = region; this.shippingBaseRate = shippingBaseRate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public double getShippingBaseRate() { return shippingBaseRate; }
    public void setShippingBaseRate(double shippingBaseRate) { this.shippingBaseRate = shippingBaseRate; }
}