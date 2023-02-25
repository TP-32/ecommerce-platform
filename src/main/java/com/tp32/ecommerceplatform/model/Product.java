package com.tp32.ecommerceplatform.model;

import jakarta.persistence.*;

/**
 * A model which holds the application data of a particular Product, used to be
 * displayed within a view.
 */
@Entity
@Table(name="products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name", nullable = false, length = 25)
    private String name;

    @Column(name = "image", nullable = false, length = 255)
    private String image;

    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @Column(name = "price", nullable = false)
    private Float price;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Category category;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "inventory_id", referencedColumnName = "id")
    private Inventory inventory;

    public Product(String name, String image, String description, Float price) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
    }
    
    public Product() {}

    // Getters
    public Long getID() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getImage() {
        return this.image;
    }

    public String getDescription() {
        return this.description;
    }

    public Float getPrice() {
        return this.price;
    }

    public Category getCategory() {
        return this.category;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
