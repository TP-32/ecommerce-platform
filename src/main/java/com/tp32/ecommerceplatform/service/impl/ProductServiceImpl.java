package com.tp32.ecommerceplatform.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tp32.ecommerceplatform.dto.ProductDto;
import com.tp32.ecommerceplatform.model.Product;
import com.tp32.ecommerceplatform.repository.ProductRepository;
import com.tp32.ecommerceplatform.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProduct(Long id) {
        if (productRepository.existsById(id)) {
            return productRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public Product createProduct(ProductDto productDto) {
        if (productRepository.existsByName(productDto.getName())) {
            // A product with this name already exists
        }

        // Validate the Data Transfer Object before the product is made, and then save it afterwards.
        // Errors can be thrown if there is an issue, instead of returning.

        Product product = new Product(productDto.getName(), productDto.getDescription(), productDto.getImage(), productDto.getPrice());

        // Product needs to be validated first
        productRepository.save(product); // Saves the product to the database.
        return product;
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        // Update the id of a product with the new Product
        Product updateProduct = productRepository.findById(id).get();
        updateProduct.setName(product.getName());

        return updateProduct;
    }

    @Override
    public Product deleteProduct(Long id) {
        Product product = productRepository.findById(id).get();
        productRepository.deleteById(id);
        return product;
    }

    @Override
    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();

        // Populate the products list with data from the database
        products = productRepository.findAll();
        System.out.println(products);
        
        return products;
    }
    
}
