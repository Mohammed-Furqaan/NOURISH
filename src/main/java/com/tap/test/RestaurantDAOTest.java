package com.tap.test;

import java.math.BigDecimal;
import java.sql.Time;
import com.tap.DAO.RestaurantDAO;
import com.tap.DAOImpl.RestaurantDAOImpl;
import com.tap.model.Restaurant;

public class RestaurantDAOTest {

    public static void main(String[] args) {
        try {
            RestaurantDAO dao = new RestaurantDAOImpl();
            String email = "test_rest_" + System.currentTimeMillis() + "@gmail.com";
            String phone = "9" + (System.currentTimeMillis() % 1000000000L);
            Restaurant restaurant = new Restaurant(
                1,                              
                "Test Empty Image Rest",
                "No images provided",
                email,
                phone,
                "", // empty logo
                "", // empty banner
                Time.valueOf("09:00:00"),
                Time.valueOf("22:00:00"),
                30,
                new BigDecimal("100.00"),
                new BigDecimal("30.00"),
                4.0,
                0,
                true,
                "APPROVED"
            );

            System.out.println("Attempting to insert restaurant with empty logo/banner...");
            int result = dao.addRestaurant(restaurant);
            System.out.println("Insert result code: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}