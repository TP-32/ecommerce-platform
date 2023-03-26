package com.tp32.ecommerceplatform.dto;

/**
 * Data Transfer Object which will send information from the front-end to the back-end.
 */
public class NetDto {
    
    private int quantity;

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
