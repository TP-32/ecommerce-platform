package com.tp32.ecommerceplatform.model;

import jakarta.persistence.*;

@Entity
@Table(name="products")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column
    // private Category category;

    // @Column
    // private Inventory inventory;

    @Column(name = "product_name", nullable = false, length = 25)
    private String productName;

    @Column(name = "image", nullable = false, length = 255)
    private String image;

    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @Column(name = "price", nullable = false)
    private Integer price;
}
