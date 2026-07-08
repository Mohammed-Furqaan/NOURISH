package com.tap.test;

import java.math.BigDecimal;

import com.tap.DAO.CartDAO;
import com.tap.DAOImpl.CartDAOImpl;
import com.tap.model.Cart;

public class CartDAOTest {

    public static void main(String[] args) {

        CartDAO dao = new CartDAOImpl();

        Cart cart = new Cart(

                2,                      // user_id (must exist)
                2,                      // restaurant_id (must exist)
                new BigDecimal("0.00"),
                0

        );

        int result = dao.addCart(cart);

        if (result > 0) {
            System.out.println("Cart Added Successfully...");
        } else {
            System.out.println("Failed to Add Cart...");
        }

    }

}