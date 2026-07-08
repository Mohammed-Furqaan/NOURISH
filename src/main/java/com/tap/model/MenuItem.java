package com.tap.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class MenuItem {

    private int itemId;
    private int restaurantId;
    private int categoryId;
    private String itemName;
    private String description;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private String foodType;
    private int preparationTime;
    private Integer calories;
    private int quantityAvailable;
    private String imageUrl;
    private BigDecimal rating;
    private int totalReviews;
    private boolean isAvailable;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Default Constructor
    public MenuItem() {

    }

    // Constructor without Primary Key
    public MenuItem(int restaurantId,
                    int categoryId,
                    String itemName,
                    String description,
                    BigDecimal price,
                    BigDecimal discountPrice,
                    String foodType,
                    int preparationTime,
                    Integer calories,
                    int quantityAvailable,
                    String imageUrl,
                    BigDecimal rating,
                    int totalReviews,
                    boolean isAvailable) {

        this.restaurantId = restaurantId;
        this.categoryId = categoryId;
        this.itemName = itemName;
        this.description = description;
        this.price = price;
        this.discountPrice = discountPrice;
        this.foodType = foodType;
        this.preparationTime = preparationTime;
        this.calories = calories;
        this.quantityAvailable = quantityAvailable;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.totalReviews = totalReviews;
        this.isAvailable = isAvailable;
    }

    // Constructor with Primary Key
    public MenuItem(int itemId,
                    int restaurantId,
                    int categoryId,
                    String itemName,
                    String description,
                    BigDecimal price,
                    BigDecimal discountPrice,
                    String foodType,
                    int preparationTime,
                    Integer calories,
                    int quantityAvailable,
                    String imageUrl,
                    BigDecimal rating,
                    int totalReviews,
                    boolean isAvailable,
                    Timestamp createdAt,
                    Timestamp updatedAt) {

        this.itemId = itemId;
        this.restaurantId = restaurantId;
        this.categoryId = categoryId;
        this.itemName = itemName;
        this.description = description;
        this.price = price;
        this.discountPrice = discountPrice;
        this.foodType = foodType;
        this.preparationTime = preparationTime;
        this.calories = calories;
        this.quantityAvailable = quantityAvailable;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.totalReviews = totalReviews;
        this.isAvailable = isAvailable;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public int getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(int totalReviews) {
        this.totalReviews = totalReviews;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
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
        return "MenuItem [itemId=" + itemId +
                ", restaurantId=" + restaurantId +
                ", categoryId=" + categoryId +
                ", itemName=" + itemName +
                ", description=" + description +
                ", price=" + price +
                ", discountPrice=" + discountPrice +
                ", foodType=" + foodType +
                ", preparationTime=" + preparationTime +
                ", calories=" + calories +
                ", quantityAvailable=" + quantityAvailable +
                ", imageUrl=" + imageUrl +
                ", rating=" + rating +
                ", totalReviews=" + totalReviews +
                ", isAvailable=" + isAvailable +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt + "]";
    }
}