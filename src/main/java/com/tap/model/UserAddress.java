package com.tap.model;

import java.sql.Timestamp;

public class UserAddress {

    private int addressId;
    private int userId;
    private String addressType;
    private String houseNo;
    private String street;
    private String area;
    private String landmark;
    private String city;
    private String state;
    private String pincode;
    private double latitude;
    private double longitude;
    private boolean isDefault;
    private Timestamp createdAt;

    // Default Constructor
    public UserAddress() {

    }

    // Constructor without Primary Key
    public UserAddress(int userId,
                       String addressType,
                       String houseNo,
                       String street,
                       String area,
                       String landmark,
                       String city,
                       String state,
                       String pincode,
                       double latitude,
                       double longitude,
                       boolean isDefault) {

        this.userId = userId;
        this.addressType = addressType;
        this.houseNo = houseNo;
        this.street = street;
        this.area = area;
        this.landmark = landmark;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isDefault = isDefault;
    }

    // Constructor with Primary Key
    public UserAddress(int addressId,
                       int userId,
                       String addressType,
                       String houseNo,
                       String street,
                       String area,
                       String landmark,
                       String city,
                       String state,
                       String pincode,
                       double latitude,
                       double longitude,
                       boolean isDefault,
                       Timestamp createdAt) {

        this.addressId = addressId;
        this.userId = userId;
        this.addressType = addressType;
        this.houseNo = houseNo;
        this.street = street;
        this.area = area;
        this.landmark = landmark;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isDefault = isDefault;
        this.createdAt = createdAt;
    }

    // Getters and Setters

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "UserAddress [addressId=" + addressId +
                ", userId=" + userId +
                ", city=" + city +
                ", state=" + state +
                ", pincode=" + pincode +
                "]";
    }
}