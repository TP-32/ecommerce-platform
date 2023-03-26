package com.tp32.ecommerceplatform.service;

import java.util.List;

import com.tp32.ecommerceplatform.model.OrderItem;
import com.tp32.ecommerceplatform.service.impl.OrderItemsServiceImpl.Popular;

public interface OrderItemsService {
   OrderItem save(OrderItem orderItem);
   OrderItem getOrderItem(Long orderId, Long productId);
   List<OrderItem> getAllOrderItems();
   Popular popularProduct();
   List<OrderItem> removeOrderItem(Long orderId, Long productId);
}
