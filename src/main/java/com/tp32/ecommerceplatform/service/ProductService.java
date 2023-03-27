package com.tp32.ecommerceplatform.service;

import java.util.List;

import com.tp32.ecommerceplatform.dto.ProductDto;
import com.tp32.ecommerceplatform.model.Product;

public interface ProductService {
    Product getProduct(Long id);
    Product createProduct(ProductDto productDto);
    Product updateProduct(Long id, ProductDto productDto);
    Product deleteProduct(Long id);
    List<Product> getProducts();
    List<Product> getProductsWithSort(String field, String direction);
    List<Product> getProductsWithSort(List<Product> products, String field, String direction);
    long count();
}
