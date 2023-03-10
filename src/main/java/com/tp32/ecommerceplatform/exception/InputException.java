package com.tp32.ecommerceplatform.exception;

import org.springframework.http.HttpStatus;

public class InputException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public InputException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() { return this.status; }
    
    @Override
    public String getMessage() { return this.message; }
    
}
