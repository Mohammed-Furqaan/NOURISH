package com.tap.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.tap.DAO.CouponUsageDAO;
import com.tap.model.CouponUsage;
import com.tap.utility.DBConnection;

public class CouponUsageDAOImpl implements CouponUsageDAO {

    private static final String INSERT =
            "INSERT INTO coupon_usage(coupon_id, user_id, order_id) VALUES (?, ?, ?)";

    @Override
    public int addCouponUsage(CouponUsage u) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT)) {

            ps.setInt(1, u.getCouponId());
            ps.setInt(2, u.getUserId());
            ps.setInt(3, u.getOrderId());

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}