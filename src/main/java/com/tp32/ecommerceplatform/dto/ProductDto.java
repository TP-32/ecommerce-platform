package com.tp32.ecommerceplatform.dto;;

public class ProductDto {
    
    private String name;
    private String description;
    private String image;
    private String category;
    private Float price;
    private Integer stock;

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
