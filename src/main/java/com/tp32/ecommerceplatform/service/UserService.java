package com.tp32.ecommerceplatform.service;

import java.util.List;

import com.tp32.ecommerceplatform.dto.JwtResponse;
import com.tp32.ecommerceplatform.dto.LoginDto;
import com.tp32.ecommerceplatform.dto.RegisterDto;
import com.tp32.ecommerceplatform.dto.UpdateUserDto;
import com.tp32.ecommerceplatform.model.Role;
import com.tp32.ecommerceplatform.model.User;

/**
 * Abstract implementation of services required for the User.
 */
public interface UserService {
    JwtResponse login(LoginDto loginDto);
    JwtResponse register(RegisterDto registerDto);
    User getUser(Long id);
    User updateUser(Long id, UpdateUserDto userDto);
    User deleteUser(Long id);
    List<User> getUsers();
    List<User> getUsersWithSort(String field, String direction);
    List<Role> getRoles();
    long count();
}
