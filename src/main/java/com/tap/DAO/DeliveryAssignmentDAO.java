package com.tap.DAO;

import com.tap.model.DeliveryAssignment;

public interface DeliveryAssignmentDAO {

    int assignDelivery(DeliveryAssignment assignment);

    DeliveryAssignment getByOrderId(int orderId);

    int updateStatus(int orderId, String status);
}