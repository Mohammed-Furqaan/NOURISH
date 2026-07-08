package com.tap.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class RestaurantAddress {

    private int addressId;
    private int restaurantId;
    private String buildingName;
    private String street;
    private String area;
    private String landmark;
    private String city;
    private String state;
    private String pincode;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Timestamp createdAt;

    // Default Constructor
    public RestaurantAddress() {

    }

    // Constructor without Primary Key
    public RestaurantAddress(int restaurantId,
                             String buildingName,
                             String street,
                             String area,
                             String landmark,
                             String city,
                             String state,
                             String pincode,
                             BigDecimal latitude,
                             BigDecimal longitude) {

        this.restaurantId = restaurantId;
        this.buildingName = buildingName;
        this.street = street;
        this.area = area;
        this.landmark = landmark;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Constructor with Primary Key
    public RestaurantAddress(int addressId,
                             int restaurantId,
                             String buildingName,
                             String street,
                             String area,
                             String landmark,
                             String city,
                             String state,
                             String pincode,
                             BigDecimal latitude,
                             BigDecimal longitude,
                             Timestamp createdAt) {

        this.addressId = addressId;
        this.restaurantId = restaurantId;
        this.buildingName = buildingName;
        this.street = street;
        this.area = area;
        this.landmark = landmark;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createdAt = createdAt;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
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

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "RestaurantAddress [addressId=" + addressId +
                ", restaurantId=" + restaurantId +
                ", buildingName=" + buildingName +
                ", street=" + street +
                ", area=" + area +
                ", landmark=" + landmark +
                ", city=" + city +
                ", state=" + state +
                ", pincode=" + pincode +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", createdAt=" + createdAt + "]";
    }
}