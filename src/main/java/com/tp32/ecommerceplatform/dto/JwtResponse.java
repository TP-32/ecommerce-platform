package com.tp32.ecommerceplatform.dto;

/**
 * Data Transfer Object which will send information from the front-end to the
 * back-end.
 * For this Object, it is handling the Token that determines who the User is.
 */
public class JwtResponse {

    private String accessToken;
    private String tokenType = "Bearer";

    public String getToken() {
        return this.accessToken;
    }

    public String getTokenType() {
        return this.tokenType;
    }

    public void setToken(String token) {
        this.accessToken = token;
    }

}
