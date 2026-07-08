package com.tap.test;

import com.tap.DAO.MenuImageDAO;
import com.tap.DAOImpl.MenuImageDAOImpl;
import com.tap.model.MenuItemImage;

public class MenuImageDAOTest {

    public static void main(String[] args) {

        MenuImageDAO dao = new MenuImageDAOImpl();

        MenuItemImage image = new MenuItemImage(

                1,      // item_id (must exist in menu_items table)
                "images/menu/farmhouse_pizza_1.png"

        );

        int result = dao.addImage(image);

        if (result > 0) {
            System.out.println("Menu Image Added Successfully...");
        } else {
            System.out.println("Failed to Add Menu Image...");
        }

    }

}