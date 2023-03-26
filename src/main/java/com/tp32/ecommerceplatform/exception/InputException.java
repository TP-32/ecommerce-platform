package com.tp32.ecommerceplatform.exception;

public class InputException extends RuntimeException {

    private String message;

    public InputException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
