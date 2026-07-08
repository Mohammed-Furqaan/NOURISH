package com.tap.model;

import java.sql.Timestamp;

public class RestaurantImage {

    private int imageId;
    private int restaurantId;
    private String imageUrl;
    private String imageType;
    private Timestamp uploadedAt;

    // Default Constructor
    public RestaurantImage() {

    }

    // Constructor without Primary Key
    public RestaurantImage(int restaurantId,
                           String imageUrl,
                           String imageType) {

        this.restaurantId = restaurantId;
        this.imageUrl = imageUrl;
        this.imageType = imageType;
    }

    // Constructor with Primary Key
    public RestaurantImage(int imageId,
                           int restaurantId,
                           String imageUrl,
                           String imageType,
                           Timestamp uploadedAt) {

        this.imageId = imageId;
        this.restaurantId = restaurantId;
        this.imageUrl = imageUrl;
        this.imageType = imageType;
        this.uploadedAt = uploadedAt;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public Timestamp getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Timestamp uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    @Override
    public String toString() {
        return "RestaurantImage [imageId=" + imageId +
                ", restaurantId=" + restaurantId +
                ", imageUrl=" + imageUrl +
                ", imageType=" + imageType +
                ", uploadedAt=" + uploadedAt + "]";
    }

}