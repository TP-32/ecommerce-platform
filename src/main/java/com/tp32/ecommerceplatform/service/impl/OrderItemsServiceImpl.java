package com.tp32.ecommerceplatform.service.impl;

import org.springframework.stereotype.Service;

import com.tp32.ecommerceplatform.model.Order;
import com.tp32.ecommerceplatform.model.OrderItem;
import com.tp32.ecommerceplatform.model.Product;
import com.tp32.ecommerceplatform.service.OrderItemsService;

@Service
public class OrderItemsServiceImpl implements OrderItemsService {

    @Override
    public OrderItem addOrderItem(Order order, Product product) {
        throw new UnsupportedOperationException("Unimplemented method 'addOrderItem'");
    }
}
