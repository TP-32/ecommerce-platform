package com.tp32.ecommerceplatform.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object which will send information from the front-end to the
 * back-end.
 */
public class AddressDto {

    @NotEmpty
    @Size(min = 5, message = "First Line length must be greater than 5.")
    @Size(max = 50, message = "First Line length must be less than 50.")
    private String firstLine;

    @Size(max = 50, message = "Second Line length must be less than 50.")
    private String secondLine;

    private String country;

    @Size(min = 1, message = "City length must be greater than 1.")
    @Size(max = 50, message = "City length must be less than 50.")
    private String city;

    @Pattern(regexp = "^\\d{3,}$|^\\d{5,}$|^[A-Z]{1,2}\\d{1,2}[A-Z]?$|^\\d{3,}-\\d{3,}$|^\\d{3,}\\s\\d{2,}$|^[A-Z]{1,2}\\d{1,2}\\s?\\d[A-Z]{2}$|^[A-Z]{1,2}\\d{1}[A-Z]\\s?\\d[A-Z]{2}$", message = "Please enter a vlaid postcode.")
    private String postcode;

    public AddressDto() {
    }

    public String getFirstLine() {
        return this.firstLine;
    }

    public String getSecondLine() {
        return this.secondLine;
    }

    public String getCountry() {
        return this.country;
    }

    public String getCity() {
        return this.city;
    }

    public String getPostcode() {
        return this.postcode;
    }

    public void setFirstLine(String firstLine) {
        this.firstLine = firstLine;
    }

    public void setSecondLine(String secondLine) {
        this.secondLine = secondLine;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

}
