package com.tap.DAO;

import java.util.List;

import com.tap.model.OrderItem;

public interface OrderItemDAO {

    // Insert
    int addOrderItem(OrderItem orderItem);

    // Fetch
    OrderItem getOrderItemById(int orderItemId);

    List<OrderItem> getOrderItemsByOrderId(int orderId);

    List<OrderItem> getAllOrderItems();
    

    // Update
    int updateOrderItem(OrderItem orderItem);

    // Delete
    int deleteOrderItem(int orderItemId);

}