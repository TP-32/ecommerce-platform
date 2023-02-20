package com.tp32.ecommerceplatform.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tp32.ecommerceplatform.model.Role;

/**
 * Used to manage the data within the Spring Boot application.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
