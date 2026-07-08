package com.tap.test;

import java.math.BigDecimal;

import com.tap.DAO.MenuItemDAO;
import com.tap.DAOImpl.MenuItemDAOImpl;
import com.tap.model.MenuItem;

public class MenuDAOTest {

    public static void main(String[] args) {

        MenuItemDAO dao = new MenuItemDAOImpl();

        MenuItem menu = new MenuItem(

                2,                                  // restaurant_id
                1,                                  // category_id
                "Farmhouse Pizza",
                "Loaded with fresh vegetables and cheese",
                new BigDecimal("349.00"),
                new BigDecimal("299.00"),
                "VEG",
                20,
                650,
                100,
                "images/menu/farmhouse_pizza.png",
                new BigDecimal("4.5"),
                120,
                true

        );

        int result = dao.addMenuItem(menu);

        if (result > 0) {
            System.out.println("Menu Item Added Successfully...");
        } else {
            System.out.println("Failed to Add Menu Item...");
        }

    }

}