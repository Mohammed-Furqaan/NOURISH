package com.tap.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class OrderItem {

    private int orderItemId;
    private int orderId;
    private int itemId;
    private int quantity;
    private BigDecimal price;
    private BigDecimal subtotal;
    private Timestamp createdAt;

    // Default Constructor
    public OrderItem() {

    }

    // Constructor without Primary Key
    public OrderItem(int orderId,
                     int itemId,
                     int quantity,
                     BigDecimal price,
                     BigDecimal subtotal) {

        this.orderId = orderId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = subtotal;
    }

    // Constructor with Primary Key
    public OrderItem(int orderItemId,
                     int orderId,
                     int itemId,
                     int quantity,
                     BigDecimal price,
                     BigDecimal subtotal,
                     Timestamp createdAt) {

        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = subtotal;
        this.createdAt = createdAt;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "OrderItem [orderItemId=" + orderItemId +
                ", orderId=" + orderId +
                ", itemId=" + itemId +
                ", quantity=" + quantity +
                ", price=" + price +
                ", subtotal=" + subtotal +
                ", createdAt=" + createdAt + "]";
    }
}