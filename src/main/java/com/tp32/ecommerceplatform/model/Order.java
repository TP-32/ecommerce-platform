package com.tp32.ecommerceplatform.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    // @JoinColumn(name = "user_id")
    // private User user;

    @Column
    private Long orderTime;
    /* created_at TIMESTAMP to show what time the order was ordered at */

}
