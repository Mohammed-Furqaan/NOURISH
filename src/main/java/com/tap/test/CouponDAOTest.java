package com.tap.test;

import com.tap.DAO.CouponDAO;
import com.tap.DAOImpl.CouponDAOImpl;
import com.tap.model.Coupon;

import java.sql.Date;

public class CouponDAOTest {

    public static void main(String[] args) {

        CouponDAO dao = new CouponDAOImpl();

        Coupon c = new Coupon(
                "SAVE50",
                "Flat 50 discount",
                "PERCENTAGE",
                50,
                200,
                100,
                Date.valueOf("2026-12-31"),
                10,
                true
        );

        int result = dao.addCoupon(c);

        if (result > 0) {
            System.out.println("Coupon Added Successfully...");
        } else {
            System.out.println("Failed to Add Coupon...");
        }
    }
}