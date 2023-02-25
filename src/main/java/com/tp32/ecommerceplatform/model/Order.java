package com.tp32.ecommerceplatform.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="`order`") // Uses ` syntax to distinguish order from a keyword
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* created_at TIMESTAMP to show what time the order was ordered at */
    @Column(name = "order_time")
    private Date orderTime;

    @Column(name = "price", nullable = false)
    private Float price;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderitem_id", referencedColumnName = "id")
    private List<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    public Order() {}

    // Getters
    public Long getID() {
        return this.id;
    }

    public Date getOrderTime() {
        return this.orderTime;
    }

    public Float getPrice() {
        return this.price;
    }

    public List<OrderItem> getOrderItems() {
        return this.orderItems;
    }

    // Setters
    public void setOrderTime(Date date) {
        this.orderTime = date;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

}
