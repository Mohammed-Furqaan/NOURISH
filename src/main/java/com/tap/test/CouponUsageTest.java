package com.tap.test;

import com.tap.DAO.CouponUsageDAO;
import com.tap.DAOImpl.CouponUsageDAOImpl;
import com.tap.model.CouponUsage;

public class CouponUsageTest {

    public static void main(String[] args) {

        CouponUsageDAO dao = new CouponUsageDAOImpl();

        CouponUsage usage = new CouponUsage(
                1,   // coupon_id
                1,   // user_id
                1    // order_id
        );

        int result = dao.addCouponUsage(usage);

        if (result > 0) {
            System.out.println("Coupon Applied Successfully...");
        } else {
            System.out.println("Failed to Apply Coupon...");
        }
    }
}