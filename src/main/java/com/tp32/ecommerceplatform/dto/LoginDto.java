package com.tp32.ecommerceplatform.dto;

/**
 * Data Transfer Object which will send information from the front-end to the back-end.
 */
public class LoginDto {
    
    private String email;
    private String password;

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
