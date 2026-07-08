package com.tap.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Cart {

    private int cartId;
    private int userId;
    private int restaurantId;
    private BigDecimal totalAmount;
    private int totalItems;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Default Constructor
    public Cart() {

    }

    // Constructor without Primary Key
    public Cart(int userId,
                int restaurantId,
                BigDecimal totalAmount,
                int totalItems) {

        this.userId = userId;
        this.restaurantId = restaurantId;
        this.totalAmount = totalAmount;
        this.totalItems = totalItems;
    }

    // Constructor with Primary Key
    public Cart(int cartId,
                int userId,
                int restaurantId,
                BigDecimal totalAmount,
                int totalItems,
                Timestamp createdAt,
                Timestamp updatedAt) {

        this.cartId = cartId;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.totalAmount = totalAmount;
        this.totalItems = totalItems;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
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

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
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
        return "Cart [cartId=" + cartId +
                ", userId=" + userId +
                ", restaurantId=" + restaurantId +
                ", totalAmount=" + totalAmount +
                ", totalItems=" + totalItems +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt + "]";
    }
}