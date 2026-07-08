package com.tap.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Order {

    private int orderId;
    private int userId;
    private int restaurantId;
    private int addressId;
    private Integer couponId;
    private Timestamp orderDate;
    private BigDecimal totalAmount;
    private BigDecimal deliveryFee;
    private BigDecimal taxAmount;
    private BigDecimal discountAmount;
    private BigDecimal finalAmount;
    private String paymentMethod;
    private String paymentStatus;
    private String orderStatus;
    private Timestamp estimatedDeliveryTime;
    private Timestamp deliveredAt;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Default Constructor
    public Order() {

    }

    // Constructor without Primary Key
    public Order(int userId,
                 int restaurantId,
                 int addressId,
                 Integer couponId,
                 BigDecimal totalAmount,
                 BigDecimal deliveryFee,
                 BigDecimal taxAmount,
                 BigDecimal discountAmount,
                 BigDecimal finalAmount,
                 String paymentMethod,
                 String paymentStatus,
                 String orderStatus,
                 Timestamp estimatedDeliveryTime,
                 Timestamp deliveredAt) {

        this.userId = userId;
        this.restaurantId = restaurantId;
        this.addressId = addressId;
        this.couponId = couponId;
        this.totalAmount = totalAmount;
        this.deliveryFee = deliveryFee;
        this.taxAmount = taxAmount;
        this.discountAmount = discountAmount;
        this.finalAmount = finalAmount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.orderStatus = orderStatus;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.deliveredAt = deliveredAt;
    }

    // Constructor with Primary Key
    public Order(int orderId,
                 int userId,
                 int restaurantId,
                 int addressId,
                 Integer couponId,
                 Timestamp orderDate,
                 BigDecimal totalAmount,
                 BigDecimal deliveryFee,
                 BigDecimal taxAmount,
                 BigDecimal discountAmount,
                 BigDecimal finalAmount,
                 String paymentMethod,
                 String paymentStatus,
                 String orderStatus,
                 Timestamp estimatedDeliveryTime,
                 Timestamp deliveredAt,
                 Timestamp createdAt,
                 Timestamp updatedAt) {

        this.orderId = orderId;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.addressId = addressId;
        this.couponId = couponId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.deliveryFee = deliveryFee;
        this.taxAmount = taxAmount;
        this.discountAmount = discountAmount;
        this.finalAmount = finalAmount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.orderStatus = orderStatus;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
        this.deliveredAt = deliveredAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(BigDecimal deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Timestamp getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(Timestamp estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    public Timestamp getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(Timestamp deliveredAt) {
        this.deliveredAt = deliveredAt;
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
        return "Order [orderId=" + orderId +
                ", userId=" + userId +
                ", restaurantId=" + restaurantId +
                ", addressId=" + addressId +
                ", couponId=" + couponId +
                ", orderDate=" + orderDate +
                ", totalAmount=" + totalAmount +
                ", deliveryFee=" + deliveryFee +
                ", taxAmount=" + taxAmount +
                ", discountAmount=" + discountAmount +
                ", finalAmount=" + finalAmount +
                ", paymentMethod=" + paymentMethod +
                ", paymentStatus=" + paymentStatus +
                ", orderStatus=" + orderStatus +
                ", estimatedDeliveryTime=" + estimatedDeliveryTime +
                ", deliveredAt=" + deliveredAt +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt + "]";
    }
}