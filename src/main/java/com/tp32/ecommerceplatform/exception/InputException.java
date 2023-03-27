package com.tp32.ecommerceplatform.exception;

public class InputException extends RuntimeException {

    private int status;
    private String message;

    public InputException(int status, String message) {
        super(message);
        this.status = status;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
    
    public int getStatus() {
        return this.status;
    }

}
