package com.tp32.ecommerceplatform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tp32.ecommerceplatform.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Boolean existsByName(String name);
    List<Product> findAll();
}
