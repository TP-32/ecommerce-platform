package com.tp32.ecommerceplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tp32.ecommerceplatform.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
