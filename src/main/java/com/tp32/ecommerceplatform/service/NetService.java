package com.tp32.ecommerceplatform.service;

import java.util.List;

import com.tp32.ecommerceplatform.model.Net;
import com.tp32.ecommerceplatform.model.Product;
import com.tp32.ecommerceplatform.model.User;

public interface NetService {
    Net addProduct(User user, Product product);
    List<Product> getProducts(User user);
}
