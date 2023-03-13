package com.tp32.ecommerceplatform.service;

import java.util.List;

import com.tp32.ecommerceplatform.dto.ProductDto;
import com.tp32.ecommerceplatform.model.Product;

public interface ProductService {
    Product getProduct(Long id);
    Product createProduct(ProductDto productDto);
    Product updateProduct(Long id, Product product);
    Product deleteProduct(Long id);
    List<Product> getProducts();
}
