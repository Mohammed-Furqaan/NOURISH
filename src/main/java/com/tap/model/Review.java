package com.tap.model;

import java.sql.Timestamp;

public class Review {

    private int reviewId;
    private int userId;
    private int restaurantId;
    private int orderId;
    private int rating;
    private String reviewText;
    private Timestamp createdAt;

    // Default Constructor
    public Review() {
    }

    // Constructor without PK
    public Review(int userId, int restaurantId, int orderId, int rating, String reviewText) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.orderId = orderId;
        this.rating = rating;
        this.reviewText = reviewText;
    }

    // Constructor with PK
    public Review(int reviewId, int userId, int restaurantId, int orderId,
                  int rating, String reviewText, Timestamp createdAt) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.orderId = orderId;
        this.rating = rating;
        this.reviewText = reviewText;
        this.createdAt = createdAt;
    }

    // Getters & Setters

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Review [reviewId=" + reviewId +
                ", userId=" + userId +
                ", restaurantId=" + restaurantId +
                ", orderId=" + orderId +
                ", rating=" + rating +
                ", reviewText=" + reviewText +
                ", createdAt=" + createdAt + "]";
    }
}