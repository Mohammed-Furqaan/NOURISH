package com.tap.model;

import java.sql.Timestamp;

public class Favorite {

    private int favoriteId;
    private int userId;
    private int restaurantId;
    private Timestamp createdAt;

    // Default Constructor
    public Favorite() {
    }

    // Constructor without PK
    public Favorite(int userId, int restaurantId) {
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    // Constructor with PK
    public Favorite(int favoriteId, int userId, int restaurantId, Timestamp createdAt) {
        this.favoriteId = favoriteId;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.createdAt = createdAt;
    }

    public int getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Favorite [favoriteId=" + favoriteId +
                ", userId=" + userId +
                ", restaurantId=" + restaurantId +
                ", createdAt=" + createdAt + "]";
    }
}