package com.tap.DAO;

import java.util.List;
import com.tap.model.Coupon;

public interface CouponDAO {

    int addCoupon(Coupon coupon);

    Coupon getCouponById(int id);

    Coupon getCouponByCode(String code);

    List<Coupon> getAllCoupons();

    int updateCoupon(Coupon coupon);

    int deleteCoupon(int id);
}