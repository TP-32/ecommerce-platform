package com.tp32.ecommerceplatform.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private User user;

    @Column
    private Long orderTime;
    /* created_at TIMESTAMP to show what time the order was ordered at */

}
