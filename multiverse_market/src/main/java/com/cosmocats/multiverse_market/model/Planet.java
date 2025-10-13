package com.cosmocats.multiverse_market.model;

import java.util.UUID;

public class Planet {
    private String id = UUID.randomUUID().toString();
    private String name;
    private String region;
    private double shippingBaseRate;

    public Planet() {}
    public Planet(String name, String region, double shippingBaseRate) {
        this.name = name; this.region = region; this.shippingBaseRate = shippingBaseRate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

