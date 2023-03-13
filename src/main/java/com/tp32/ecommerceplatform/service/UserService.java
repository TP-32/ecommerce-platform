package com.tp32.ecommerceplatform.service;

import com.tp32.ecommerceplatform.dto.JwtResponse;
import com.tp32.ecommerceplatform.dto.LoginDto;
import com.tp32.ecommerceplatform.dto.RegisterDto;

/**
 * Abstract implementation of services required for the User.
 */
public interface UserService {
    JwtResponse login(LoginDto loginDto);
    JwtResponse register(RegisterDto registerDto);
}
