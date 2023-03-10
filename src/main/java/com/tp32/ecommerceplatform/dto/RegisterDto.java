package com.tp32.ecommerceplatform.dto;

/**
 * Data Transfer Object which will send information from the front-end to the back-end.
 */
public class RegisterDto {
    
    private String firstName;
    private String lastName;
    private String email;
    private String password;

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
    
}
