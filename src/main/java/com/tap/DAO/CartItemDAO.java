package com.tap.DAO;

import java.util.List;

import com.tap.model.CartItem;

public interface CartItemDAO {

    // Insert
    int addCartItem(CartItem cartItem);

    // Fetch
    CartItem getCartItemById(int cartItemId);

    List<CartItem> getCartItemsByCartId(int cartId);

    List<CartItem> getAllCartItems();
    
    CartItem getCartItem(int cartId, int itemId);

    // Update
    int updateCartItem(CartItem cartItem);

    // Delete
    int deleteCartItem(int cartItemId);

}