package com.tp32.ecommerceplatform.service;

public class OrderItemsService {
    private OrderItemsRepository orderItemsRepository;

    public void addOrderedProducts(OrderItem orderItem) {
        orderItemsRepository.save(orderItem);
        
    }
}
