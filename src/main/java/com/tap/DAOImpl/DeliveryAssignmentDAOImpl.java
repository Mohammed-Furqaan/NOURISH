package com.tap.DAOImpl;

import com.tap.DAO.DeliveryAssignmentDAO;
import com.tap.model.DeliveryAssignment;
import com.tap.utility.DBConnection;

import java.sql.*;

public class DeliveryAssignmentDAOImpl implements DeliveryAssignmentDAO {

    private static final String INSERT =
            "INSERT INTO delivery_assignments(order_id, partner_id, delivery_status) VALUES (?, ?, ?)";

    private static final String SELECT_BY_ORDER =
            "SELECT * FROM delivery_assignments WHERE order_id=?";

    private static final String UPDATE_STATUS =
            "UPDATE delivery_assignments SET delivery_status=? WHERE order_id=?";

    @Override
    public int assignDelivery(DeliveryAssignment a) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT)) {

            ps.setInt(1, a.getOrderId());
            ps.setInt(2, a.getPartnerId());
            ps.setString(3, a.getDeliveryStatus());

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public DeliveryAssignment getByOrderId(int orderId) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_ORDER)) {

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                DeliveryAssignment d = new DeliveryAssignment();

                d.setAssignmentId(rs.getInt("assignment_id"));
                d.setOrderId(rs.getInt("order_id"));
                d.setPartnerId(rs.getInt("partner_id"));
                d.setDeliveryStatus(rs.getString("delivery_status"));
                d.setAssignedAt(rs.getTimestamp("assigned_at"));
                d.setPickedUpAt(rs.getTimestamp("picked_up_at"));
                d.setDeliveredAt(rs.getTimestamp("delivered_at"));

                return d;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int updateStatus(int orderId, String status) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_STATUS)) {

            ps.setString(1, status);
            ps.setInt(2, orderId);

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}