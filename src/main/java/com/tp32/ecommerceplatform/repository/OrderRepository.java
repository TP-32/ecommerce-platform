package com.tp32.ecommerceplatform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tp32.ecommerceplatform.model.Order;
import com.tp32.ecommerceplatform.model.Status;
import com.tp32.ecommerceplatform.model.User;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.user = ?1")
    List<Order> findAllByUser(User user);
    @Query("SELECT SUM(price) FROM Order")
    Float sumPrice();
    List<Order> findAll();
    @Query("SELECT COUNT(*) o FROM Order o WHERE o.status = ?1")
    int count(Status status);
}
