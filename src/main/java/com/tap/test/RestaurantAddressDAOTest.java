package com.tap.test;

import java.math.BigDecimal;

import com.tap.DAO.RestaurantAddressDAO;
import com.tap.DAOImpl.RestaurantAddressDAOImpl;
import com.tap.model.RestaurantAddress;

public class RestaurantAddressDAOTest {

    public static void main(String[] args) {

        RestaurantAddressDAO dao = new RestaurantAddressDAOImpl();

        RestaurantAddress address = new RestaurantAddress(

                2,
                "Prestige Towers",
                "MG Road",
                "Ashok Nagar",
                "Near Metro Station",
                "Bangalore",
                "Karnataka",
                "560001",
                new BigDecimal("12.97160000"),
                new BigDecimal("77.59460000")

        );

        int result = dao.addAddress(address);

        if(result > 0) {
            System.out.println("Restaurant Address Added Successfully...");
        }
        else {
            System.out.println("Failed to Add Restaurant Address...");
        }

    }

}