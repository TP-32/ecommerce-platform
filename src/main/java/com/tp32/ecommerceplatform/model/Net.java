package com.tp32.ecommerceplatform.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="net")
public class Net {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private Integer quantity;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private List<Product> products;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Net() {}

    // Getters
    public Long getID() {
        return this.id;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public User getUser() {
        return this.user;
    }

    // Setters
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
