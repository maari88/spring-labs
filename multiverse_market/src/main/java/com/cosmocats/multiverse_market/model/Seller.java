package com.cosmocats.multiverse_market.model;

import jakarta.persistence.*;

@Entity
@Table(name = "SELLERS")
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "planet_id")
    private Planet planet;

    @Column(name = "contact_info")
    private String contact;

    public Seller() {}

    public Seller(String name, Planet planet, String contact) {
        this.name = name;
        this.planet = planet;
        this.contact = contact;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Planet getPlanet() { return planet; }
    public void setPlanet(Planet planet) { this.planet = planet; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
}