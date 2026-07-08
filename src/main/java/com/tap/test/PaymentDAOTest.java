package com.tap.test;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.tap.DAO.PaymentDAO;
import com.tap.DAOImpl.PaymentDAOImpl;
import com.tap.model.Payment;

public class PaymentDAOTest {

    public static void main(String[] args) {

        PaymentDAO dao = new PaymentDAOImpl();

        Payment payment = new Payment(

                1,                                  // order_id (must exist)
                "TXN123456789",                    // transaction_id (can be null for COD)
                "UPI",                             // COD / UPI / CARD / NET_BANKING / WALLET
                new BigDecimal("656.00"),          // amount
                "PENDING",                         // PENDING / SUCCESS / FAILED / REFUNDED
                new Timestamp(System.currentTimeMillis()) // payment_date
        );

        int result = dao.addPayment(payment);

        if (result > 0) {
            System.out.println("Payment Added Successfully...");
        } else {
            System.out.println("Failed to Add Payment...");
        }

    }
}