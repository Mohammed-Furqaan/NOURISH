package com.tap.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Payment {

    private int paymentId;
    private int orderId;
    private String transactionId;
    private String paymentMethod;
    private BigDecimal amount;
    private String paymentStatus;
    private Timestamp paymentDate;
    private Timestamp createdAt;

    // Default Constructor
    public Payment() {

    }

    // Constructor without Primary Key
    public Payment(int orderId,
                   String transactionId,
                   String paymentMethod,
                   BigDecimal amount,
                   String paymentStatus,
                   Timestamp paymentDate) {

        this.orderId = orderId;
        this.transactionId = transactionId;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
    }

    // Constructor with Primary Key
    public Payment(int paymentId,
                   int orderId,
                   String transactionId,
                   String paymentMethod,
                   BigDecimal amount,
                   String paymentStatus,
                   Timestamp paymentDate,
                   Timestamp createdAt) {

        this.paymentId = paymentId;
        this.orderId = orderId;
        this.transactionId = transactionId;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
        this.createdAt = createdAt;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Payment [paymentId=" + paymentId +
                ", orderId=" + orderId +
                ", transactionId=" + transactionId +
                ", paymentMethod=" + paymentMethod +
                ", amount=" + amount +
                ", paymentStatus=" + paymentStatus +
                ", paymentDate=" + paymentDate +
                ", createdAt=" + createdAt + "]";
    }
}