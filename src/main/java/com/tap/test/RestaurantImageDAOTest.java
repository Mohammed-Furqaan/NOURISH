package com.tap.test;

import com.tap.DAO.RestaurantImageDAO;
import com.tap.DAOImpl.RestaurantImageDAOImpl;
import com.tap.model.RestaurantImage;

public class RestaurantImageDAOTest {

    public static void main(String[] args) {

        RestaurantImageDAO dao = new RestaurantImageDAOImpl();

        RestaurantImage image = new RestaurantImage(

                2,                                      // restaurant_id (must exist)
                "images/restaurants/pizza_logo.png",
                "LOGO"

        );

        int result = dao.addImage(image);

        if(result > 0) {
            System.out.println("Restaurant Image Added Successfully...");
        }
        else {
            System.out.println("Failed to Add Restaurant Image...");
        }

    }

}