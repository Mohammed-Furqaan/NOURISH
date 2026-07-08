package com.tap.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tap.DAO.CartDAO;
import com.tap.model.Cart;
import com.tap.utility.DBConnection;

public class CartDAOImpl implements CartDAO {

    private static final String INSERT_CART =
            "INSERT INTO cart (user_id, restaurant_id, total_amount, total_items) VALUES (?, ?, ?, ?)";

    private static final String GET_CART_BY_ID =
            "SELECT * FROM cart WHERE cart_id = ?";

    private static final String GET_CART_BY_USER_ID =
            "SELECT * FROM cart WHERE user_id = ?";

    private static final String GET_ALL_CARTS =
            "SELECT * FROM cart";

    private static final String UPDATE_CART =
            "UPDATE cart SET user_id = ?, restaurant_id = ?, total_amount = ?, total_items = ? WHERE cart_id = ?";

    private static final String DELETE_CART =
            "DELETE FROM cart WHERE cart_id = ?";

    @Override
    public int addCart(Cart cart) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(INSERT_CART)) {

            pstmt.setInt(1, cart.getUserId());
            pstmt.setInt(2, cart.getRestaurantId());
            pstmt.setBigDecimal(3, cart.getTotalAmount());
            pstmt.setInt(4, cart.getTotalItems());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public Cart getCartById(int cartId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_CART_BY_ID)) {

            pstmt.setInt(1, cartId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractCart(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Cart getCartByUserId(int userId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_CART_BY_USER_ID)) {

            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractCart(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Cart> getAllCarts() {

        List<Cart> cartList = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_ALL_CARTS)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                cartList.add(extractCart(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cartList;
    }

    @Override
    public int updateCart(Cart cart) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(UPDATE_CART)) {

            pstmt.setInt(1, cart.getUserId());
            pstmt.setInt(2, cart.getRestaurantId());
            pstmt.setBigDecimal(3, cart.getTotalAmount());
            pstmt.setInt(4, cart.getTotalItems());
            pstmt.setInt(5, cart.getCartId());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int deleteCart(int cartId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(DELETE_CART)) {

            pstmt.setInt(1, cartId);

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private Cart extractCart(ResultSet rs) throws SQLException {

        return new Cart(

                rs.getInt("cart_id"),
                rs.getInt("user_id"),
                rs.getInt("restaurant_id"),
                rs.getBigDecimal("total_amount"),
                rs.getInt("total_items"),
                rs.getTimestamp("created_at"),
                rs.getTimestamp("updated_at")

        );
    }

}