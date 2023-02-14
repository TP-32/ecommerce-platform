package com.tp32.ecommerceplatform.model;

import jakarta.persistence.*;

@Entity
@Table(name= "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name", nullable = false, length = 25)
    private String categoryName;

}
