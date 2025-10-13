package com.cosmocats.multiverse_market.model;

import java.util.UUID;

public class Buyer {
    private String id = UUID.randomUUID().toString();
    private String username;
    private String email;

    public Buyer() {}
    public Buyer(String username, String email) {
        this.username = username; this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

