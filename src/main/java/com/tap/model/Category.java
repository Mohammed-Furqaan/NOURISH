package com.tap.model;

import java.sql.Timestamp;

public class Category {

    private int categoryId;
    private String categoryName;
    private String description;
    private String categoryImage;
    private boolean isActive;
    private Timestamp createdAt;

    // Default Constructor
    public Category() {

    }

    // Constructor without Primary Key
    public Category(String categoryName,
                    String description,
                    String categoryImage,
                    boolean isActive) {

        this.categoryName = categoryName;
        this.description = description;
        this.categoryImage = categoryImage;
        this.isActive = isActive;
    }

    // Constructor with Primary Key
    public Category(int categoryId,
                    String categoryName,
                    String description,
                    String categoryImage,
                    boolean isActive,
                    Timestamp createdAt) {

        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
        this.categoryImage = categoryImage;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Category [categoryId=" + categoryId +
                ", categoryName=" + categoryName +
                ", description=" + description +
                ", categoryImage=" + categoryImage +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt + "]";
    }
}