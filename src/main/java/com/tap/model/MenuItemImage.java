package com.tap.model;

import java.sql.Timestamp;

public class MenuItemImage {

    private int imageId;
    private int itemId;
    private String imageUrl;
    private Timestamp uploadedAt;

    // Default Constructor
    public MenuItemImage() {

    }

    // Constructor without Primary Key
    public MenuItemImage(int itemId, String imageUrl) {

        this.itemId = itemId;
        this.imageUrl = imageUrl;
    }

    // Constructor with Primary Key
    public MenuItemImage(int imageId,
                         int itemId,
                         String imageUrl,
                         Timestamp uploadedAt) {

        this.imageId = imageId;
        this.itemId = itemId;
        this.imageUrl = imageUrl;
        this.uploadedAt = uploadedAt;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Timestamp getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Timestamp uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    @Override
    public String toString() {
        return "MenuItemImage [imageId=" + imageId +
                ", itemId=" + itemId +
                ", imageUrl=" + imageUrl +
                ", uploadedAt=" + uploadedAt + "]";
    }
}