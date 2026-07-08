package com.tap.model;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;

public class Restaurant {

    private int restaurantId;
    private int ownerId;
    private String restaurantName;
    private String description;
    private String email;
    private String phoneNumber;
    private String logo;
    private String bannerImage;
    private Time openingTime;
    private Time closingTime;
    private int averageDeliveryTime;
    private BigDecimal minimumOrderAmount;
    private BigDecimal deliveryFee;
    private double rating;
    private int totalReviews;
    private boolean isOpen;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Default Constructor
    public Restaurant() {

    }

    // Constructor without Primary Key
    public Restaurant(int ownerId,
                      String restaurantName,
                      String description,
                      String email,
                      String phoneNumber,
                      String logo,
                      String bannerImage,
                      Time openingTime,
                      Time closingTime,
                      int averageDeliveryTime,
                      BigDecimal minimumOrderAmount,
                      BigDecimal deliveryFee,
                      double rating,
                      int totalReviews,
                      boolean isOpen,
                      String status) {

        this.ownerId = ownerId;
        this.restaurantName = restaurantName;
        this.description = description;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.logo = logo;
        this.bannerImage = bannerImage;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.averageDeliveryTime = averageDeliveryTime;
        this.minimumOrderAmount = minimumOrderAmount;
        this.deliveryFee = deliveryFee;
        this.rating = rating;
        this.totalReviews = totalReviews;
        this.isOpen = isOpen;
        this.status = status;
    }

    // Constructor with Primary Key
    public Restaurant(int restaurantId,
                      int ownerId,
                      String restaurantName,
                      String description,
                      String email,
                      String phoneNumber,
                      String logo,
                      String bannerImage,
                      Time openingTime,
                      Time closingTime,
                      int averageDeliveryTime,
                      BigDecimal minimumOrderAmount,
                      BigDecimal deliveryFee,
                      double rating,
                      int totalReviews,
                      boolean isOpen,
                      String status,
                      Timestamp createdAt,
                      Timestamp updatedAt) {

        this.restaurantId = restaurantId;
        this.ownerId = ownerId;
        this.restaurantName = restaurantName;
        this.description = description;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.logo = logo;
        this.bannerImage = bannerImage;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.averageDeliveryTime = averageDeliveryTime;
        this.minimumOrderAmount = minimumOrderAmount;
        this.deliveryFee = deliveryFee;
        this.rating = rating;
        this.totalReviews = totalReviews;
        this.isOpen = isOpen;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters & Setters

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public Time getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(Time openingTime) {
        this.openingTime = openingTime;
    }

    public Time getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Time closingTime) {
        this.closingTime = closingTime;
    }

    public int getAverageDeliveryTime() {
        return averageDeliveryTime;
    }

    public void setAverageDeliveryTime(int averageDeliveryTime) {
        this.averageDeliveryTime = averageDeliveryTime;
    }

    public BigDecimal getMinimumOrderAmount() {
        return minimumOrderAmount;
    }

    public void setMinimumOrderAmount(BigDecimal minimumOrderAmount) {
        this.minimumOrderAmount = minimumOrderAmount;
    }

    public BigDecimal getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(BigDecimal deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(int totalReviews) {
        this.totalReviews = totalReviews;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Restaurant [restaurantId=" + restaurantId +
                ", restaurantName=" + restaurantName +
                ", ownerId=" + ownerId +
                ", rating=" + rating +
                ", isOpen=" + isOpen + "]";
    }
}