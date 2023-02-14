package com.tp32.ecommerceplatform.model;

import jakarta.persistence.*;

@Entity
@Table(name="orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private User user;

    /* created_at TIMESTAMP required */

}
