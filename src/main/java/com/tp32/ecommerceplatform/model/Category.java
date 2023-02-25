package com.tp32.ecommerceplatform.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name= "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name", nullable = false, length = 25)
    private String categoryName;

    @Column(name="description", length = 255)
    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private List<Product> products;

    public Category() {}

    // Getters
    public Long getID() {
        return this.id;
    }

    public String getName() {
        return this.categoryName;
    }

    public String getDescription() {
        return this.description;
    }

    public List<Product> getProducts() {
        return this.products;
    }

    // Setters
    public void setName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
