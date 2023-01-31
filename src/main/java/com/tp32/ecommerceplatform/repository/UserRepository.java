package com.tp32.ecommerceplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tp32.ecommerceplatform.model.User;

/**
 * Used to manage the data within the Spring Boot application.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByFirstName(String firstName);
    User findByLastName(String lastName);
    User findByEmail(String emaiL);
    
}
