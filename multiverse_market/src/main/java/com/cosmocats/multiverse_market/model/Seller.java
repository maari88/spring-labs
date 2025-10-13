package com.cosmocats.multiverse_market.model;

import java.util.UUID;

public class Seller {
    private String id = UUID.randomUUID().toString();
    private String name;
    private String planetId;
    private String contact;

    public Seller() {}
    public Seller(String name, String planetId, String contact) {
        this.name = name; this.planetId = planetId; this.contact = contact;
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

    public String getPlanetId() {
        return planetId;
    }

    public void setPlanetId(String planetId) {
        this.planetId = planetId;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}

