package com.tp32.ecommerceplatform.model;

import jakarta.persistence.*;

/**
 * A model which holds the application data of a particular Net Item, used to be
 * displayed within a view.
 */
@Entity
@Table(name="netitem")
public class NetItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer quantity;

    @ManyToOne
    private Net net;

    @ManyToOne
    private Product product;

    public NetItem() {}

    // Getters
    public Long getID() {
        return this.id;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public Net getNet() {
        return this.net;
    }

    public Product getProduct() {
        return this.product;
    }

    // Setters
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setNet(Net net) {
        this.net = net;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
