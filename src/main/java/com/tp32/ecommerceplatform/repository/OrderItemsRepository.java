package com.tp32.ecommerceplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tp32.ecommerceplatform.model.OrderItem;

public interface OrderItemsRepository extends JpaRepository<OrderItem, Long> {

}
