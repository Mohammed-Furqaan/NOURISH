package com.tap.DAO;

import java.util.List;
import com.tap.model.Order;

public interface OrderDAO {

    // Insert
    int addOrder(Order order);

    // NEW METHOD
    int addOrderAndReturnId(Order order);

    // Fetch
    Order getOrderById(int orderId);

    List<Order> getOrdersByUserId(int userId);

    List<Order> getOrdersByRestaurantId(int restaurantId);

    List<Order> getAllOrders();

    // Update
    int updateOrder(Order order);

    // Delete
    int deleteOrder(int orderId);
    
    int getOrderCount();

    double getTotalRevenue();
}