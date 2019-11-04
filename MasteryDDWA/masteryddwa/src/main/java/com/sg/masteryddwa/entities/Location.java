/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.masteryddwa.entities;

import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author daler
 */
public class Location {
    
    private int id;
    @NotBlank(message = "Address cannot be empty.")
    @Size(max = 50, message = "Address cannot be more than 50 characters.")
    private String address;
    @NotBlank(message = "City cannot be empty.")
    @Size(max = 30, message = "City cannot be more than 30 characters.")
    private String city;
    @NotBlank(message = "State cannot be blank.")
    @Size(max = 2, message = "State cannot be more than 2 characters. Please use the standard postal abbreviations (e.g., MN, WI, etc.).")
    private String state;
    @Size(max = 5, message = "ZIP code cannot be more than 5 characters. Please use a standard 5 digit ZIP code, or leave this field blank.")
    private String zip;
    @Digits(integer = 2, fraction = 6, message = "Latitude must be a number between 0 and 90 with no more than 6 digits after the decimal point.")
    @Min(value = 0, message = "Latitude must be greater than or equal to 0.")
    @Max(value = 90, message = "Latitutde must be less than or equal to 90.")
    @NotNull(message = "Latitude cannot be blank.")
    private BigDecimal latitude;
    @Digits(integer = 3, fraction = 6, message = "Longitude must be a number between 0 and 180 with no more than 6 digits after the decimal point.")
    @Min(value = 0, message = "Longitude must be greater than or equal to 0.")
    @Max(value = 180, message = "Longitude must be less than or equal to 180.")
    @NotNull(message = "Longitude cannot be blank.")
    private BigDecimal longitude;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this.id;
        hash = 37 * hash + Objects.hashCode(this.address);
        hash = 37 * hash + Objects.hashCode(this.city);
        hash = 37 * hash + Objects.hashCode(this.state);
        hash = 37 * hash + Objects.hashCode(this.zip);
        hash = 37 * hash + Objects.hashCode(this.latitude);
        hash = 37 * hash + Objects.hashCode(this.longitude);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Location other = (Location) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.zip, other.zip)) {
            return false;
        }
        if (!Objects.equals(this.latitude, other.latitude)) {
            return false;
        }
        if (!Objects.equals(this.longitude, other.longitude)) {
            return false;
        }
        return true;
    }


}
