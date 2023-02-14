package com.tp32.ecommerceplatform.model;

import jakarta.persistence.*;

@Entity
@Table(name="products")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Category category;

    @Column
    private Inventory inventory;

    @Column(name = "product_name", nullable = false, length = 25)
    private String productName;

    /* image required */

    @Column(name = "price", nullable = false)
    private Integer price;
}
