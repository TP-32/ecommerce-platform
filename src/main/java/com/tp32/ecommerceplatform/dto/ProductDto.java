package com.tp32.ecommerceplatform.dto;

import com.tp32.ecommerceplatform.model.Product;

import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object which will send information from the front-end to the
 * back-end.
 */
public class ProductDto {
    
    @Size(min = 5, message = "Name length must be greater than 5.")
    @Size(max = 100, message = "Name length must be less than 100.")
    private String name;

    @Size(min = 5, message = "Description length must be greater than 5.")
    @Size(max = 250, message = "Description length must be less than 250.")
    private String description;

    private String image;
    private String category;
    private Float price;
    private Integer stock;

    public ProductDto() {}

    public ProductDto(Product product) {
        this.name = product.getName();
        this.description = product.getDescription();
        this.image = product.getImage();
        this.category = product.getCategory().getName();
        this.price = product.getPrice();
        this.stock = product.getInventory().getStock();
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getImage() {
        return this.image;
    }
    
    public String getCategory() {
        return this.category;
    }

    public Float getPrice() {
        return this.price;
    }

    public Integer getStock() {
        return this.stock;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
