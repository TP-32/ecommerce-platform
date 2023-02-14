package com.tp32.ecommerceplatform.model;

import jakarta.persistence.*;

@Entity
@Table(name="orderItems")
public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer quantity;

    @Column
    private Orders orders;

    @Column
    private Products products;

}
