package com.tp32.ecommerceplatform.model;

import jakarta.persistence.*;

@Entity
@Table(name="inventory")
public class Inventory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer stock;

    public Inventory() {}

    // Getters
    public Long getID() {
        return this.id;
    }

    public Integer getStock() {
        return this.stock;
    }

    // Setters
    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
