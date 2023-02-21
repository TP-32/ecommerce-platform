package com.tp32.ecommerceplatform.model;

import jakarta.persistence.*;

@Entity
@Table(name="orderitem")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer quantity;

    // @Column
    // private Order orders;

    // @Column
    // private Products products;

}
