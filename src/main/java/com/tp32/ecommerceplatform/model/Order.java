package com.tp32.ecommerceplatform.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

/**
 * A model which holds the application data of a particular Order, used to be
 * displayed within a view.
 */
@Entity
@Table(name="`order`") // Uses ` syntax to distinguish order from a keyword
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* created_at TIMESTAMP to show what time the order was ordered at */
    @Column(name = "order_time")
    private Date time;

    @Column(name = "price", nullable = false)
    private Float price;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private Status status;

    public Order() {}

    // Getters
    public Long getID() {
        return this.id;
    }

    public Date getTime() {
        return this.time;
    }

    public Float getPrice() {
        return this.price;
    }

    public User getUser() {
        return this.user;
    }

    public Status getStatus() {
        return this.status;
    }

    public List<OrderItem> getOrderItems() {
        return this.orderItems;
    }

    // Setters
    public void setTime(Date date) {
        this.time = date;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

}
