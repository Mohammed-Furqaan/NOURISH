package com.tap.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tap.DAO.CartItemDAO;
import com.tap.model.CartItem;
import com.tap.utility.DBConnection;

public class CartItemDAOImpl implements CartItemDAO {

    private static final String INSERT_CART_ITEM =
            "INSERT INTO cart_items (cart_id, item_id, quantity, price, subtotal) VALUES (?, ?, ?, ?, ?)";

    private static final String GET_CART_ITEM_BY_ID =
            "SELECT * FROM cart_items WHERE cart_item_id = ?";

    private static final String GET_CART_ITEMS_BY_CART_ID =
            "SELECT * FROM cart_items WHERE cart_id = ?";
    
    private static final String GET_CART_ITEM =
            "SELECT * FROM cart_items WHERE cart_id=? AND item_id=?";

    private static final String GET_ALL_CART_ITEMS =
            "SELECT * FROM cart_items";

    private static final String UPDATE_CART_ITEM =
            "UPDATE cart_items SET cart_id = ?, item_id = ?, quantity = ?, price = ?, subtotal = ? WHERE cart_item_id = ?";

    private static final String DELETE_CART_ITEM =
            "DELETE FROM cart_items WHERE cart_item_id = ?";

    @Override
    public int addCartItem(CartItem cartItem) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(INSERT_CART_ITEM)) {

            pstmt.setInt(1, cartItem.getCartId());
            pstmt.setInt(2, cartItem.getItemId());
            pstmt.setInt(3, cartItem.getQuantity());
            pstmt.setBigDecimal(4, cartItem.getPrice());
            pstmt.setBigDecimal(5, cartItem.getSubtotal());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public CartItem getCartItemById(int cartItemId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_CART_ITEM_BY_ID)) {

            pstmt.setInt(1, cartItemId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractCartItem(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    @Override
    public CartItem getCartItem(int cartId, int itemId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt =
                     connection.prepareStatement(GET_CART_ITEM)) {

            pstmt.setInt(1, cartId);
            pstmt.setInt(2, itemId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                return extractCartItem(rs);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return null;
    }

    @Override
    public List<CartItem> getCartItemsByCartId(int cartId) {

        List<CartItem> cartItems = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_CART_ITEMS_BY_CART_ID)) {

            pstmt.setInt(1, cartId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                cartItems.add(extractCartItem(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cartItems;
    }

    @Override
    public List<CartItem> getAllCartItems() {

        List<CartItem> cartItems = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_ALL_CART_ITEMS)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                cartItems.add(extractCartItem(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cartItems;
    }

    @Override
    public int updateCartItem(CartItem cartItem) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(UPDATE_CART_ITEM)) {

            pstmt.setInt(1, cartItem.getCartId());
            pstmt.setInt(2, cartItem.getItemId());
            pstmt.setInt(3, cartItem.getQuantity());
            pstmt.setBigDecimal(4, cartItem.getPrice());
            pstmt.setBigDecimal(5, cartItem.getSubtotal());
            pstmt.setInt(6, cartItem.getCartItemId());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int deleteCartItem(int cartItemId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(DELETE_CART_ITEM)) {

            pstmt.setInt(1, cartItemId);

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private CartItem extractCartItem(ResultSet rs) throws SQLException {

        return new CartItem(

                rs.getInt("cart_item_id"),
                rs.getInt("cart_id"),
                rs.getInt("item_id"),
                rs.getInt("quantity"),
                rs.getBigDecimal("price"),
                rs.getBigDecimal("subtotal"),
                rs.getTimestamp("added_at")

        );
    }
}