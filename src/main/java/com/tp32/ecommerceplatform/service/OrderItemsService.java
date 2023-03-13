package com.tp32.ecommerceplatform.service;

import com.tp32.ecommerceplatform.model.OrderItem;
import com.tp32.ecommerceplatform.repository.OrderItemsRepository;

public class OrderItemsService {
    
    private OrderItemsRepository orderItemsRepository;

    public void addOrderedProducts(OrderItem orderItem) {
        orderItemsRepository.save(orderItem);
        
    }
}
