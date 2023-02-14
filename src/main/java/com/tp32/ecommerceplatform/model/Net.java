package com.tp32.ecommerceplatform.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="net")
public class Net {

    @Column
    public OrderItems orderItems;
}
