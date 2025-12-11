package com.cosmocats.multiverse_market.model;

public class Planet {
    private Long id;
    private String name;
    private String region;
    private double shippingBaseRate;

    public Planet() {}

    public Planet(Long id, String name, String region, double shippingBaseRate) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.shippingBaseRate = shippingBaseRate;
    }

    public Planet(String name, String region, double shippingBaseRate) {
        this.name = name;
        this.region = region;
        this.shippingBaseRate = shippingBaseRate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public double getShippingBaseRate() {
        return shippingBaseRate;
    }

    public void setShippingBaseRate(double shippingBaseRate) {
        this.shippingBaseRate = shippingBaseRate;
    }
}