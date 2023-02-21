package com.tp32.ecommerceplatform.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tp32.ecommerceplatform.dto.LoginDto;
import com.tp32.ecommerceplatform.dto.RegisterDto;
import com.tp32.ecommerceplatform.exception.InputException;
import com.tp32.ecommerceplatform.model.Role;
import com.tp32.ecommerceplatform.model.User;
import com.tp32.ecommerceplatform.repository.RoleRepository;
import com.tp32.ecommerceplatform.repository.UserRepository;
import com.tp32.ecommerceplatform.security.jwt.JwtToken;
import com.tp32.ecommerceplatform.service.UserService;

/**
 * Concrete implementation of UserService.
 */
@Service
public class UserServiceImpl implements UserService {

    private AuthenticationManager authManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtToken jwtToken;

    public UserServiceImpl(AuthenticationManager authManager, UserRepository userRepository,
            RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtToken jwtToken) {
        this.authManager = authManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtToken = jwtToken;
    }

    /**
     * Validates the user with the details stored in the database
     */
    @Override
    public String login(LoginDto loginDto) {
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = jwtToken.generateToken(auth, userRepository);

        return token;
    }

    /**
     * Validates the registered user against any pre-existing users.
     * Creates a new user, with inputted values and the default role (USER)
     * Saves the user to the database and returns a response to the client
     */
    @Override
    public String register(RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new InputException(HttpStatus.BAD_REQUEST, "This email is already being used.");
        }

        User user = new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Role userRole = roleRepository.findByName("ROLE_USER").get();
        user.setRole(userRole);

        userRepository.save(user);
        return "User has registered successfully.";
        //return new ResponseEntity<>("User has been successfully registered", HttpStatus.OK);
    }
}
