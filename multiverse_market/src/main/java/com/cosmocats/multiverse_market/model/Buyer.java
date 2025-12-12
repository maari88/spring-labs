package com.cosmocats.multiverse_market.model;

import jakarta.persistence.*;

@Entity
@Table(name = "BUYERS")
public class Buyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "buyer_id")
    private Long id;

    @Column(nullable = false)
    private String username;

    private String email;

    public Buyer() {}

    public Buyer(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public Buyer(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
