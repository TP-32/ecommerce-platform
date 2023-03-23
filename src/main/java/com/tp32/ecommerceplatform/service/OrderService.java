package com.tp32.ecommerceplatform.service;

import java.util.List;

import com.tp32.ecommerceplatform.dto.UpdateOrderDto;
import com.tp32.ecommerceplatform.model.Order;
import com.tp32.ecommerceplatform.model.Status;

public interface OrderService {
    Order getOrder(Long id);
    Order updateOrder(Long id, UpdateOrderDto orderDto);
    List<Order> getOrders();
    List<Order> getOrdersWithSort(String field, String direction);
    Status getStatus(Long id);
    List<Status> getStatus();
    Float sumPrice();
    long count();
}
