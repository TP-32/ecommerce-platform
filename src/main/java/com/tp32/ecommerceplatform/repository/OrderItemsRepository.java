package com.tp32.ecommerceplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tp32.ecommerceplatform.model.Order;
import com.tp32.ecommerceplatform.model.OrderItem;
import com.tp32.ecommerceplatform.model.Product;

public interface OrderItemsRepository extends JpaRepository<OrderItem, Long> {
   @Query("SELECT o FROM OrderItem o WHERE o.order = ?1 and o.product = ?2")
    OrderItem findByOrderProduct(Order order, Product product);
}
