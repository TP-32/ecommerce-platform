package com.tp32.ecommerceplatform.dto;

/**
 * Data Transfer Object which will send information from the front-end to the back-end.
 */
public class UpdateOrderDto {
    
    private Float price;
    private String status;

    public Float getPrice() {
        return this.price;
    }

    public String getStatus() {
        return this.status;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
