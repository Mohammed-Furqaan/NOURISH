package com.tap.model;

import java.sql.Timestamp;

public class DeliveryAssignment {

    private int assignmentId;
    private int orderId;
    private int partnerId;
    private String deliveryStatus;
    private Timestamp assignedAt;
    private Timestamp pickedUpAt;
    private Timestamp deliveredAt;

    public DeliveryAssignment() {
    }

    public DeliveryAssignment(int orderId, int partnerId, String deliveryStatus) {
        this.orderId = orderId;
        this.partnerId = partnerId;
        this.deliveryStatus = deliveryStatus;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(int partnerId) {
        this.partnerId = partnerId;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Timestamp getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(Timestamp assignedAt) {
        this.assignedAt = assignedAt;
    }

    public Timestamp getPickedUpAt() {
        return pickedUpAt;
    }

    public void setPickedUpAt(Timestamp pickedUpAt) {
        this.pickedUpAt = pickedUpAt;
    }

    public Timestamp getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(Timestamp deliveredAt) {
        this.deliveredAt = deliveredAt;
    }
}