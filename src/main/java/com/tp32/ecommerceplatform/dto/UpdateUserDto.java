package com.tp32.ecommerceplatform.dto;

import com.tp32.ecommerceplatform.model.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object which will send information from the front-end to the
 * back-end.
 */
public class UpdateUserDto {

    @Size(min = 1, message = "First Name length must be greater than 1.")
    @Size(max = 50, message = "First Name length must be less than 50.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First Name must only contain upper or lowercase letters.")
    private String firstName;

    @Size(min = 1, message = "Last Name length must be greater than 1.")
    @Size(max = 50, message = "Last Name length must be less than 50.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last Name must only contain upper or lowercase letters.")
    private String lastName;

    @Email(message = "Must be a valid email.")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}|$", message = "Password must be at least 8 characters and contain at least 1 capital letter, 1 number and 1 special character.")
    private String password;

    private String role;

    public UpdateUserDto() {
    }

    public UpdateUserDto(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.role = user.getRole().getName();
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getRole() {
        return this.role;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
