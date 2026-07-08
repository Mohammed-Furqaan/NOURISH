package com.tap.DAO;

import java.util.List;

import com.tap.model.Payment;

public interface PaymentDAO {

    // Insert
    int addPayment(Payment payment);

    // Fetch
    Payment getPaymentById(int paymentId);

    Payment getPaymentByOrderId(int orderId);

    List<Payment> getAllPayments();

    // Update
    int updatePayment(Payment payment);

    // Delete
    int deletePayment(int paymentId);

}