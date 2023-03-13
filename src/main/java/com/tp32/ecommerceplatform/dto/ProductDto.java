package com.tp32.ecommerceplatform.dto;

public class ProductDto {
    
    private String name;
    private String description;
    private String image;
    private Float price;

    public ProductDto() {}

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getImage() {
        return this.image;
    }

    public Float getPrice() {
        return this.price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

}
