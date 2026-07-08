package com.tap.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tap.DAO.CategoryDAO;
import com.tap.model.Category;
import com.tap.utility.DBConnection;

public class CategoryDAOImpl implements CategoryDAO {

    private static final String INSERT_CATEGORY =
            "INSERT INTO categories(category_name, description, category_image, is_active) VALUES (?, ?, ?, ?)";

    private static final String GET_CATEGORY_BY_ID =
            "SELECT * FROM categories WHERE category_id = ?";

    private static final String GET_CATEGORY_BY_NAME =
            "SELECT * FROM categories WHERE category_name = ?";

    private static final String GET_ALL_CATEGORIES =
            "SELECT * FROM categories";

    private static final String GET_ACTIVE_CATEGORIES =
            "SELECT * FROM categories WHERE is_active = true";

    private static final String UPDATE_CATEGORY =
            "UPDATE categories SET category_name = ?, description = ?, category_image = ?, is_active = ? WHERE category_id = ?";

    private static final String DELETE_CATEGORY =
            "DELETE FROM categories WHERE category_id = ?";
    private static final String CATEGORY_COUNT =
    		"SELECT COUNT(*) FROM categories";

    @Override
    public int addCategory(Category category) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(INSERT_CATEGORY)) {

            pstmt.setString(1, category.getCategoryName());
            pstmt.setString(2, category.getDescription());
            pstmt.setString(3, category.getCategoryImage());
            pstmt.setBoolean(4, category.isActive());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public Category getCategoryById(int categoryId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_CATEGORY_BY_ID)) {

            pstmt.setInt(1, categoryId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractCategory(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Category getCategoryByName(String categoryName) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_CATEGORY_BY_NAME)) {

            pstmt.setString(1, categoryName);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractCategory(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Category> getAllCategories() {

        List<Category> categoryList = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_ALL_CATEGORIES)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                categoryList.add(extractCategory(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoryList;
    }

    @Override
    public List<Category> getActiveCategories() {

        List<Category> categoryList = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_ACTIVE_CATEGORIES)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                categoryList.add(extractCategory(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoryList;
    }

    @Override
    public int updateCategory(Category category) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(UPDATE_CATEGORY)) {

            pstmt.setString(1, category.getCategoryName());
            pstmt.setString(2, category.getDescription());
            pstmt.setString(3, category.getCategoryImage());
            pstmt.setBoolean(4, category.isActive());
            pstmt.setInt(5, category.getCategoryId());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int deleteCategory(int categoryId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(DELETE_CATEGORY)) {

            pstmt.setInt(1, categoryId);

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private Category extractCategory(ResultSet rs) throws SQLException {

        return new Category(

                rs.getInt("category_id"),
                rs.getString("category_name"),
                rs.getString("description"),
                rs.getString("category_image"),
                rs.getBoolean("is_active"),
                rs.getTimestamp("created_at")

        );
    }
    
    @Override
    public int getCategoryCount() {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(CATEGORY_COUNT)) {

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