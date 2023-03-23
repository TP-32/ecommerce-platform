package com.tp32.ecommerceplatform.service;

import com.tp32.ecommerceplatform.model.Order;
import com.tp32.ecommerceplatform.model.OrderItem;
import com.tp32.ecommerceplatform.model.Product;

public interface OrderItemsService {
   OrderItem addOrderItem(Order order, Product product);
}
