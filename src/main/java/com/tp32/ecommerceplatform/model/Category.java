package com.tp32.ecommerceplatform.model;

import java.util.List;

import jakarta.persistence.*;

/**
 * A model which holds the application data of a particular Category, used to be
 * displayed within a view.
 */
@Entity
@Table(name= "category")
public class Category implements Comparable<Category> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name", nullable = false, length = 25)
    private String name;

    @Column(name="description", length = 255)
    private String description;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    public Category() {}

    // Getters
    public Long getID() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int compareTo(Category anotherCategory) {
        return Math.toIntExact(this.getID() - anotherCategory.getID());
    }
}
