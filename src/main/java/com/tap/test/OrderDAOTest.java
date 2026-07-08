package com.tap.test;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.tap.DAO.OrderDAO;
import com.tap.DAOImpl.OrderDAOImpl;
import com.tap.model.Order;

public class OrderDAOTest {

    public static void main(String[] args) {

        OrderDAO dao = new OrderDAOImpl();

        Order order = new Order(

                2,                                  // user_id (must exist)
                2,                                  // restaurant_id (must exist)
                1,                                  // address_id (must exist)
                null,                               // coupon_id (null if not using coupon)

                new BigDecimal("598.00"),           // total_amount
                new BigDecimal("40.00"),            // delivery_fee
                new BigDecimal("18.00"),            // tax_amount
                new BigDecimal("0.00"),             // discount_amount
                new BigDecimal("656.00"),           // final_amount

                "COD",                             // COD / UPI / CARD / NET_BANKING / WALLET
                "PENDING",                         // PENDING / SUCCESS / FAILED / REFUNDED
                "PLACED",                          // PLACED / CONFIRMED / PREPARING / READY_FOR_PICKUP / OUT_FOR_DELIVERY / DELIVERED / CANCELLED

                new Timestamp(System.currentTimeMillis() + (45 * 60 * 1000)), // +45 minutes
                null                                // delivered_at
        );

        int result = dao.addOrder(order);

        if (result > 0) {
            System.out.println("Order Added Successfully...");
        } else {
            System.out.println("Failed to Add Order...");
        }
    }
}