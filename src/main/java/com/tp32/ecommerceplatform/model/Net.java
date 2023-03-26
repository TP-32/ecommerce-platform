package com.tp32.ecommerceplatform.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * A model which holds the application data of a particular Basket/Cart/Net, used to be
 * displayed within a view.
 */
@Entity
@Table(name="net")
public class Net {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "price", nullable = false)
    private Float price;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<NetItem> netItems;

    public Net() {}

    // Getters
    public Long getID() {
        return this.id;
    }

    public User getUser() {
        return this.user;
    }

    public Float getPrice() {
        return this.price;
    }

    public List<NetItem> getNetItems() {
        return this.netItems;
    }

    // Setters
    public void setUser(User user) {
        this.user = user;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setNetItems(List<NetItem> netItems) {
        this.netItems = netItems;
    }
}
