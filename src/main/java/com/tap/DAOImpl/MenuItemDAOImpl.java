package com.tap.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tap.DAO.MenuItemDAO;
import com.tap.model.MenuItem;
import com.tap.utility.DBConnection;

public class MenuItemDAOImpl implements MenuItemDAO {

    private static final String INSERT_MENU_ITEM =
            "INSERT INTO menu_items(restaurant_id, category_id, item_name, description, price, discount_price, food_type, preparation_time, calories, quantity_available, image_url, rating, total_reviews, is_available) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String GET_MENU_ITEM_BY_ID =
            "SELECT * FROM menu_items WHERE item_id=?";

    private static final String GET_MENU_ITEMS_BY_RESTAURANT =
            "SELECT * FROM menu_items WHERE restaurant_id=?";

    private static final String GET_MENU_ITEMS_BY_CATEGORY =
            "SELECT * FROM menu_items WHERE category_id=?";

    private static final String GET_AVAILABLE_MENU_ITEMS =
            "SELECT * FROM menu_items WHERE is_available=true";

    private static final String GET_ALL_MENU_ITEMS =
            "SELECT * FROM menu_items";

    private static final String UPDATE_MENU_ITEM =
            "UPDATE menu_items SET restaurant_id=?, category_id=?, item_name=?, description=?, price=?, discount_price=?, food_type=?, preparation_time=?, calories=?, quantity_available=?, image_url=?, rating=?, total_reviews=?, is_available=? WHERE item_id=?";

    private static final String DELETE_MENU_ITEM =
            "DELETE FROM menu_items WHERE item_id=?";
    
    private static final String MENUITEM_COUNT =
    		"SELECT COUNT(*) FROM menu_items";

    @Override
    public int addMenuItem(MenuItem menuItem) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(INSERT_MENU_ITEM)) {

            pstmt.setInt(1, menuItem.getRestaurantId());
            pstmt.setInt(2, menuItem.getCategoryId());
            pstmt.setString(3, menuItem.getItemName());
            pstmt.setString(4, menuItem.getDescription());
            pstmt.setBigDecimal(5, menuItem.getPrice());
            pstmt.setBigDecimal(6, menuItem.getDiscountPrice());
            pstmt.setString(7, menuItem.getFoodType());
            pstmt.setInt(8, menuItem.getPreparationTime());

            if (menuItem.getCalories() != null) {
                pstmt.setInt(9, menuItem.getCalories());
            } else {
                pstmt.setNull(9, java.sql.Types.INTEGER);
            }

            pstmt.setInt(10, menuItem.getQuantityAvailable());
            pstmt.setString(11, menuItem.getImageUrl());
            pstmt.setBigDecimal(12, menuItem.getRating());
            pstmt.setInt(13, menuItem.getTotalReviews());
            pstmt.setBoolean(14, menuItem.isAvailable());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public MenuItem getMenuItemById(int itemId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_MENU_ITEM_BY_ID)) {

            pstmt.setInt(1, itemId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractMenuItem(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<MenuItem> getMenuItemsByRestaurantId(int restaurantId) {

        List<MenuItem> menuItems = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_MENU_ITEMS_BY_RESTAURANT)) {

            pstmt.setInt(1, restaurantId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                menuItems.add(extractMenuItem(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return menuItems;
    }

    @Override
    public List<MenuItem> getMenuItemsByCategoryId(int categoryId) {

        List<MenuItem> menuItems = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_MENU_ITEMS_BY_CATEGORY)) {

            pstmt.setInt(1, categoryId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                menuItems.add(extractMenuItem(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return menuItems;
    }

    @Override
    public List<MenuItem> getAvailableMenuItems() {

        List<MenuItem> menuItems = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_AVAILABLE_MENU_ITEMS)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                menuItems.add(extractMenuItem(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return menuItems;
    }

    @Override
    public List<MenuItem> getAllMenuItems() {

        List<MenuItem> menuItems = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_ALL_MENU_ITEMS)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                menuItems.add(extractMenuItem(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return menuItems;
    }

    @Override
    public int updateMenuItem(MenuItem menuItem) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(UPDATE_MENU_ITEM)) {

            pstmt.setInt(1, menuItem.getRestaurantId());
            pstmt.setInt(2, menuItem.getCategoryId());
            pstmt.setString(3, menuItem.getItemName());
            pstmt.setString(4, menuItem.getDescription());
            pstmt.setBigDecimal(5, menuItem.getPrice());
            pstmt.setBigDecimal(6, menuItem.getDiscountPrice());
            pstmt.setString(7, menuItem.getFoodType());
            pstmt.setInt(8, menuItem.getPreparationTime());

            if (menuItem.getCalories() != null) {
                pstmt.setInt(9, menuItem.getCalories());
            } else {
                pstmt.setNull(9, java.sql.Types.INTEGER);
            }

            pstmt.setInt(10, menuItem.getQuantityAvailable());
            pstmt.setString(11, menuItem.getImageUrl());
            pstmt.setBigDecimal(12, menuItem.getRating());
            pstmt.setInt(13, menuItem.getTotalReviews());
            pstmt.setBoolean(14, menuItem.isAvailable());
            pstmt.setInt(15, menuItem.getItemId());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int deleteMenuItem(int itemId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(DELETE_MENU_ITEM)) {

            pstmt.setInt(1, itemId);

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private MenuItem extractMenuItem(ResultSet rs) throws SQLException {

        return new MenuItem(

                rs.getInt("item_id"),
                rs.getInt("restaurant_id"),
                rs.getInt("category_id"),
                rs.getString("item_name"),
                rs.getString("description"),
                rs.getBigDecimal("price"),
                rs.getBigDecimal("discount_price"),
                rs.getString("food_type"),
                rs.getInt("preparation_time"),
                (Integer) rs.getObject("calories"),
                rs.getInt("quantity_available"),
                rs.getString("image_url"),
                rs.getBigDecimal("rating"),
                rs.getInt("total_reviews"),
                rs.getBoolean("is_available"),
                rs.getTimestamp("created_at"),
                rs.getTimestamp("updated_at")

        );
    }
    
    @Override
    public int getMenuItemCount() {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(MENUITEM_COUNT)) {

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