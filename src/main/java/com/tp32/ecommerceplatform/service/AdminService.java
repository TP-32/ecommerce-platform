package com.tp32.ecommerceplatform.service;

import com.tp32.ecommerceplatform.dto.ProductDto;
import com.tp32.ecommerceplatform.model.Product;

/**
 * Abstract implementation of services required for Admin accounts.
 */
public interface AdminService {
    Product createProduct(ProductDto productDto);
}
