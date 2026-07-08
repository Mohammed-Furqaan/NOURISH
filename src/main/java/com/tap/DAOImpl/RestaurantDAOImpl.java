package com.tap.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

import com.tap.DAO.RestaurantDAO;
import com.tap.model.Restaurant;
import com.tap.utility.DBConnection;

public class RestaurantDAOImpl implements RestaurantDAO {

    private static final String INSERT_RESTAURANT =
            "INSERT INTO restaurants(owner_id,restaurant_name,description,email,phone_number,logo,banner_image,opening_time,closing_time,average_delivery_time,minimum_order_amount,delivery_fee,rating,total_reviews,is_open,status) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String GET_RESTAURANT_BY_ID =
            "SELECT * FROM restaurants WHERE restaurant_id=?";

    private static final String GET_ALL_RESTAURANTS =
            "SELECT * FROM restaurants";

    private static final String GET_RESTAURANTS_BY_OWNER =
            "SELECT * FROM restaurants WHERE owner_id=?";

    private static final String UPDATE_RESTAURANT =
            "UPDATE restaurants SET restaurant_name=?,description=?,email=?,phone_number=?,logo=?,banner_image=?,opening_time=?,closing_time=?,average_delivery_time=?,minimum_order_amount=?,delivery_fee=?,is_open=?,status=? WHERE restaurant_id=?";

    private static final String DELETE_RESTAURANT =
            "DELETE FROM restaurants WHERE restaurant_id=?";
    
    private static final String SEARCH_RESTAURANTS =
    		"SELECT * FROM restaurants WHERE restaurant_name LIKE ? OR description LIKE ?";
    
    private static final String RESTAURANT_COUNT =
    		"SELECT COUNT(*) FROM restaurants";
    
    @Override
    public List<Restaurant> searchRestaurants(String keyword) {

        List<Restaurant> restaurants = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(SEARCH_RESTAURANTS)) {

            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                restaurants.add(extractRestaurant(rs));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return restaurants;
    }

    @Override
    public int addRestaurant(Restaurant restaurant) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(INSERT_RESTAURANT)) {

            pstmt.setInt(1, restaurant.getOwnerId());
            pstmt.setString(2, restaurant.getRestaurantName());
            pstmt.setString(3, restaurant.getDescription());
            pstmt.setString(4, restaurant.getEmail());
            pstmt.setString(5, restaurant.getPhoneNumber());
            pstmt.setString(6, restaurant.getLogo());
            pstmt.setString(7, restaurant.getBannerImage());
            pstmt.setTime(8, restaurant.getOpeningTime());
            pstmt.setTime(9, restaurant.getClosingTime());
            pstmt.setInt(10, restaurant.getAverageDeliveryTime());
            pstmt.setBigDecimal(11, restaurant.getMinimumOrderAmount());
            pstmt.setBigDecimal(12, restaurant.getDeliveryFee());
            pstmt.setDouble(13, restaurant.getRating());
            pstmt.setInt(14, restaurant.getTotalReviews());
            pstmt.setBoolean(15, restaurant.isOpen());
            pstmt.setString(16, restaurant.getStatus());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public Restaurant getRestaurantById(int restaurantId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_RESTAURANT_BY_ID)) {

            pstmt.setInt(1, restaurantId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractRestaurant(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Restaurant> getAllRestaurants() {

        List<Restaurant> restaurants = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_ALL_RESTAURANTS)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                restaurants.add(extractRestaurant(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return restaurants;
    }

    @Override
    public List<Restaurant> getRestaurantsByOwnerId(int ownerId) {

        List<Restaurant> restaurants = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_RESTAURANTS_BY_OWNER)) {

            pstmt.setInt(1, ownerId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                restaurants.add(extractRestaurant(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return restaurants;
    }

    @Override
    public int updateRestaurant(Restaurant restaurant) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(UPDATE_RESTAURANT)) {

            pstmt.setString(1, restaurant.getRestaurantName());
            pstmt.setString(2, restaurant.getDescription());
            pstmt.setString(3, restaurant.getEmail());
            pstmt.setString(4, restaurant.getPhoneNumber());
            pstmt.setString(5, restaurant.getLogo());
            pstmt.setString(6, restaurant.getBannerImage());
            pstmt.setTime(7, restaurant.getOpeningTime());
            pstmt.setTime(8, restaurant.getClosingTime());
            pstmt.setInt(9, restaurant.getAverageDeliveryTime());
            pstmt.setBigDecimal(10, restaurant.getMinimumOrderAmount());
            pstmt.setBigDecimal(11, restaurant.getDeliveryFee());
            pstmt.setBoolean(12, restaurant.isOpen());
            pstmt.setString(13, restaurant.getStatus());
            pstmt.setInt(14, restaurant.getRestaurantId());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int deleteRestaurant(int restaurantId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(DELETE_RESTAURANT)) {

            pstmt.setInt(1, restaurantId);

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private Restaurant extractRestaurant(ResultSet rs) throws SQLException {

        return new Restaurant(

                rs.getInt("restaurant_id"),
                rs.getInt("owner_id"),
                rs.getString("restaurant_name"),
                rs.getString("description"),
                rs.getString("email"),
                rs.getString("phone_number"),
                rs.getString("logo"),
                rs.getString("banner_image"),
                rs.getTime("opening_time"),
                rs.getTime("closing_time"),
                rs.getInt("average_delivery_time"),
                rs.getBigDecimal("minimum_order_amount"),
                rs.getBigDecimal("delivery_fee"),
                rs.getDouble("rating"),
                rs.getInt("total_reviews"),
                rs.getBoolean("is_open"),
                rs.getString("status"),
                rs.getTimestamp("created_at"),
                rs.getTimestamp("updated_at")
        );
    }
    
    @Override
    public int getRestaurantCount() {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(RESTAURANT_COUNT)) {

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                return rs.getInt(1);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}