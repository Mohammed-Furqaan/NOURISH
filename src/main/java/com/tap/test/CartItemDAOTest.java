package com.tap.test;

import java.math.BigDecimal;

import com.tap.DAO.CartItemDAO;
import com.tap.DAOImpl.CartItemDAOImpl;
import com.tap.model.CartItem;

public class CartItemDAOTest {

    public static void main(String[] args) {

        CartItemDAO dao = new CartItemDAOImpl();

        CartItem cartItem = new CartItem(

                1,                          // cart_id (must exist)
                1,                          // item_id (must exist)
                2,                          // quantity
                new BigDecimal("299.00"),   // price
                new BigDecimal("598.00")    // subtotal

        );

        int result = dao.addCartItem(cartItem);

        if (result > 0) {
            System.out.println("Cart Item Added Successfully...");
        } else {
            System.out.println("Failed to Add Cart Item...");
        }

    }

}