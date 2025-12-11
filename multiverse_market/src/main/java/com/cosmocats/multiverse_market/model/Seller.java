package com.cosmocats.multiverse_market.model;

public class Seller {
    private Long id;
    private String name;
    private Long planetId;
    private String contact;
    public Seller() {}

    public Seller(Long id, String name, Long planetId, String contact) {
        this.id = id;
        this.name = name;
        this.planetId = planetId;
        this.contact = contact;
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

    public Long getPlanetId() {
        return planetId;
    }

    public void setPlanetId(Long planetId) {
        this.planetId = planetId;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}