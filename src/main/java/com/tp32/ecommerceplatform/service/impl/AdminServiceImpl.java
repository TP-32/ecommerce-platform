package com.tp32.ecommerceplatform.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tp32.ecommerceplatform.dto.ProductDto;
import com.tp32.ecommerceplatform.exception.InputException;
import com.tp32.ecommerceplatform.model.Product;
import com.tp32.ecommerceplatform.repository.CategoryRepository;
import com.tp32.ecommerceplatform.repository.ProductRepository;
import com.tp32.ecommerceplatform.service.AdminService;
import com.tp32.ecommerceplatform.service.ProductService;

/**
 * Concrete implementation of UserService.
 */
@Service
public class AdminServiceImpl implements AdminService {

    private ProductService productService;
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public AdminServiceImpl(ProductService productService, ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product createProduct(ProductDto productDto) {
        // Validate here
        if (productRepository.existsByName(productDto.getName())) {
            throw new InputException(HttpStatus.BAD_REQUEST, "A product with this name already exists.");
        }

        if (!categoryRepository.existsByName(productDto.getCategory())) {
            throw new InputException(HttpStatus.BAD_REQUEST, "An invalid category has been inputted.");
        }

        Product product = productService.createProduct(productDto);
        return product;
    }
}
