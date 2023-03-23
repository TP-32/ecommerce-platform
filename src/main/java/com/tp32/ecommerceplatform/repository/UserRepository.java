package com.tp32.ecommerceplatform.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tp32.ecommerceplatform.model.User;

/**
 * Used to manage the data within the Spring Boot application.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String emaiL);
    Boolean existsByEmail(String email);
}
