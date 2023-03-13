package com.tp32.ecommerceplatform.model;

import jakarta.persistence.*;

/**
 * A model which holds the application data of a particular Token, used to keep track of User Tokens.
 */
@Entity
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String token;

    private boolean revoked;

    private boolean expired;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

    /*
     * Constructor to initialise every value of the Token.
     */
    public Token(String token, boolean revoked, boolean expired, User user) {
        this.token = token;
        this.revoked = revoked;
        this.expired = expired;
        this.user = user;
    }

    /*
     * Empty constructor is used here to create the Object without initialising its values immediately.
     */
    public Token() {
    }
    
    // Getters
    public Long getID() {
        return this.id;
    }

    public String getToken() {
        return this.token;
    }

    public boolean isRevoked() {
        return this.revoked;
    }

    public boolean isExpired() {
        return this.expired;
    }

    public User getUser() {
        return this.user;
    }

    // Setters
    public void setToken(String token) {
        this.token = token;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
