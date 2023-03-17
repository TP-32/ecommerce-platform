package com.tp32.ecommerceplatform.model;

import jakarta.persistence.*;

/**
 * A model which holds the application data of a particular Status, used within an Order.
 */
@Entity
@Table(name = "status")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60, unique = true)
    private String name;

    public Status(String name) {
        this.name = name;
    }

    public Status() {}

    // Getters
    public Long getID() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }
}
