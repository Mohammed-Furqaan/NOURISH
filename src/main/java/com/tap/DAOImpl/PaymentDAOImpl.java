package com.tap.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.tap.DAO.PaymentDAO;
import com.tap.model.Payment;
import com.tap.utility.DBConnection;

public class PaymentDAOImpl implements PaymentDAO {

    private static final String INSERT_PAYMENT =
            "INSERT INTO payments (order_id, transaction_id, payment_method, amount, payment_status, payment_date) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String GET_PAYMENT_BY_ID =
            "SELECT * FROM payments WHERE payment_id = ?";

    private static final String GET_PAYMENT_BY_ORDER_ID =
            "SELECT * FROM payments WHERE order_id = ?";

    private static final String GET_ALL_PAYMENTS =
            "SELECT * FROM payments";

    private static final String UPDATE_PAYMENT =
            "UPDATE payments SET order_id = ?, transaction_id = ?, payment_method = ?, amount = ?, payment_status = ?, payment_date = ? WHERE payment_id = ?";

    private static final String DELETE_PAYMENT =
            "DELETE FROM payments WHERE payment_id = ?";

    @Override
    public int addPayment(Payment payment) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(INSERT_PAYMENT)) {

            pstmt.setInt(1, payment.getOrderId());

            if (payment.getTransactionId() != null) {
                pstmt.setString(2, payment.getTransactionId());
            } else {
                pstmt.setNull(2, Types.VARCHAR);
            }

            pstmt.setString(3, payment.getPaymentMethod());
            pstmt.setBigDecimal(4, payment.getAmount());
            pstmt.setString(5, payment.getPaymentStatus());
            pstmt.setTimestamp(6, payment.getPaymentDate());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public Payment getPaymentById(int paymentId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_PAYMENT_BY_ID)) {

            pstmt.setInt(1, paymentId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractPayment(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Payment getPaymentByOrderId(int orderId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_PAYMENT_BY_ORDER_ID)) {

            pstmt.setInt(1, orderId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractPayment(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Payment> getAllPayments() {

        List<Payment> paymentList = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_ALL_PAYMENTS)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                paymentList.add(extractPayment(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paymentList;
    }

    @Override
    public int updatePayment(Payment payment) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(UPDATE_PAYMENT)) {

            pstmt.setInt(1, payment.getOrderId());

            if (payment.getTransactionId() != null) {
                pstmt.setString(2, payment.getTransactionId());
            } else {
                pstmt.setNull(2, Types.VARCHAR);
            }

            pstmt.setString(3, payment.getPaymentMethod());
            pstmt.setBigDecimal(4, payment.getAmount());
            pstmt.setString(5, payment.getPaymentStatus());
            pstmt.setTimestamp(6, payment.getPaymentDate());
            pstmt.setInt(7, payment.getPaymentId());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int deletePayment(int paymentId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(DELETE_PAYMENT)) {

            pstmt.setInt(1, paymentId);

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private Payment extractPayment(ResultSet rs) throws SQLException {

        return new Payment(

                rs.getInt("payment_id"),
                rs.getInt("order_id"),
                rs.getString("transaction_id"),
                rs.getString("payment_method"),
                rs.getBigDecimal("amount"),
                rs.getString("payment_status"),
                rs.getTimestamp("payment_date"),
                rs.getTimestamp("created_at")

        );
    }
}