package com.tap.test;

import java.math.BigDecimal;

import com.tap.DAO.OrderItemDAO;
import com.tap.DAOImpl.OrderItemDAOImpl;
import com.tap.model.OrderItem;

public class OrderItemDAOTest {

    public static void main(String[] args) {

        OrderItemDAO dao = new OrderItemDAOImpl();

        OrderItem orderItem = new OrderItem(

                1,                          // order_id (must exist)
                1,                          // item_id (must exist)
                2,                          // quantity
                new BigDecimal("299.00"),   // price
                new BigDecimal("598.00")    // subtotal

        );

        int result = dao.addOrderItem(orderItem);

        if (result > 0) {
            System.out.println("Order Item Added Successfully...");
        } else {
            System.out.println("Failed to Add Order Item...");
        }

    }

}