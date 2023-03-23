package com.tp32.ecommerceplatform.model;

import jakarta.persistence.*;

/**
 * A model which holds the application data of a particular Inventory, used to be
 * displayed within a view.
 */
@Entity
@Table(name="inventory")
public class Inventory implements Comparable<Inventory> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer stock;

    @OneToOne(mappedBy="inventory")
    private Product product;

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

    @Override
    public int compareTo(Inventory anotherInventory) {
        return Math.toIntExact(this.getID() - anotherInventory.getID());
    }
}
