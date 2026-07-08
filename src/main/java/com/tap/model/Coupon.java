package com.tap.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Coupon {

    private int couponId;
    private String couponCode;
    private String description;
    private String discountType; // PERCENTAGE / FIXED
    private double discountValue;
    private double minimumOrderAmount;
    private double maximumDiscount;
    private Date expiryDate;
    private int usageLimit;
    private boolean isActive;
    private Timestamp createdAt;

    public Coupon() {}

    public Coupon(String couponCode, String description,
                  String discountType, double discountValue,
                  double minimumOrderAmount, double maximumDiscount,
                  Date expiryDate, int usageLimit, boolean isActive) {

        this.couponCode = couponCode;
        this.description = description;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.minimumOrderAmount = minimumOrderAmount;
        this.maximumDiscount = maximumDiscount;
        this.expiryDate = expiryDate;
        this.usageLimit = usageLimit;
        this.isActive = isActive;
    }

    public int getCouponId() { return couponId; }
    public void setCouponId(int couponId) { this.couponId = couponId; }

    public String getCouponCode() { return couponCode; }
    public void setCouponCode(String couponCode) { this.couponCode = couponCode; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDiscountType() { return discountType; }
    public void setDiscountType(String discountType) { this.discountType = discountType; }

    public double getDiscountValue() { return discountValue; }
    public void setDiscountValue(double discountValue) { this.discountValue = discountValue; }

    public double getMinimumOrderAmount() { return minimumOrderAmount; }
    public void setMinimumOrderAmount(double minimumOrderAmount) { this.minimumOrderAmount = minimumOrderAmount; }

    public double getMaximumDiscount() { return maximumDiscount; }
    public void setMaximumDiscount(double maximumDiscount) { this.maximumDiscount = maximumDiscount; }

    public Date getExpiryDate() { return expiryDate; }
    public void setExpiryDate(Date expiryDate) { this.expiryDate = expiryDate; }

    public int getUsageLimit() { return usageLimit; }
    public void setUsageLimit(int usageLimit) { this.usageLimit = usageLimit; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}