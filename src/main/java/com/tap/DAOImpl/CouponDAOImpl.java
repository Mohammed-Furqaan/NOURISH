package com.tap.DAOImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.tap.DAO.CouponDAO;
import com.tap.model.Coupon;
import com.tap.utility.DBConnection;

public class CouponDAOImpl implements CouponDAO {

    private static final String INSERT =
        "INSERT INTO coupons(coupon_code, description, discount_type, discount_value, minimum_order_amount, maximum_discount, expiry_date, usage_limit, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_BY_ID =
        "SELECT * FROM coupons WHERE coupon_id=?";

    private static final String SELECT_BY_CODE =
        "SELECT * FROM coupons WHERE coupon_code=?";

    private static final String SELECT_ALL =
        "SELECT * FROM coupons";

    private static final String UPDATE =
        "UPDATE coupons SET coupon_code=?, description=?, discount_type=?, discount_value=?, minimum_order_amount=?, maximum_discount=?, expiry_date=?, usage_limit=?, is_active=? WHERE coupon_id=?";

    private static final String DELETE =
        "DELETE FROM coupons WHERE coupon_id=?";

    @Override
    public int addCoupon(Coupon c) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT)) {

            ps.setString(1, c.getCouponCode());
            ps.setString(2, c.getDescription());
            ps.setString(3, c.getDiscountType());
            ps.setDouble(4, c.getDiscountValue());
            ps.setDouble(5, c.getMinimumOrderAmount());
            ps.setDouble(6, c.getMaximumDiscount());
            ps.setDate(7, c.getExpiryDate());
            ps.setInt(8, c.getUsageLimit());
            ps.setBoolean(9, c.isActive());

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public Coupon getCouponById(int id) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_ID)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return map(rs);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Coupon getCouponByCode(String code) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_CODE)) {

            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return map(rs);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Coupon> getAllCoupons() {

        List<Coupon> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public int updateCoupon(Coupon c) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {

            ps.setString(1, c.getCouponCode());
            ps.setString(2, c.getDescription());
            ps.setString(3, c.getDiscountType());
            ps.setDouble(4, c.getDiscountValue());
            ps.setDouble(5, c.getMinimumOrderAmount());
            ps.setDouble(6, c.getMaximumDiscount());
            ps.setDate(7, c.getExpiryDate());
            ps.setInt(8, c.getUsageLimit());
            ps.setBoolean(9, c.isActive());
            ps.setInt(10, c.getCouponId());

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int deleteCoupon(int id) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE)) {

            ps.setInt(1, id);
            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    private Coupon map(ResultSet rs) throws SQLException {

        Coupon c = new Coupon();

        c.setCouponId(rs.getInt("coupon_id"));
        c.setCouponCode(rs.getString("coupon_code"));
        c.setDescription(rs.getString("description"));
        c.setDiscountType(rs.getString("discount_type"));
        c.setDiscountValue(rs.getDouble("discount_value"));
        c.setMinimumOrderAmount(rs.getDouble("minimum_order_amount"));
        c.setMaximumDiscount(rs.getDouble("maximum_discount"));
        c.setExpiryDate(rs.getDate("expiry_date"));
        c.setUsageLimit(rs.getInt("usage_limit"));
        c.setActive(rs.getBoolean("is_active"));
        c.setCreatedAt(rs.getTimestamp("created_at"));

        return c;
    }
}