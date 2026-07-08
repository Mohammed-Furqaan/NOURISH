package com.tap.DAO;

import java.util.List;
import com.tap.model.Cart;

public interface CartDAO {

    // Insert
    int addCart(Cart cart);

    // Fetch
    Cart getCartById(int cartId);

    Cart getCartByUserId(int userId);

    List<Cart> getAllCarts();

    // Update
    int updateCart(Cart cart);

    // Delete
    int deleteCart(int cartId);

}