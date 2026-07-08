package com.tap.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tap.DAO.MenuImageDAO;
import com.tap.model.MenuItemImage;
import com.tap.utility.DBConnection;

public class MenuImageDAOImpl implements MenuImageDAO {

    private static final String INSERT_IMAGE =
            "INSERT INTO menu_item_images (item_id, image_url) VALUES (?, ?)";

    private static final String GET_IMAGE_BY_ID =
            "SELECT * FROM menu_item_images WHERE image_id = ?";

    private static final String GET_IMAGES_BY_ITEM_ID =
            "SELECT * FROM menu_item_images WHERE item_id = ?";

    private static final String GET_ALL_IMAGES =
            "SELECT * FROM menu_item_images";

    private static final String UPDATE_IMAGE =
            "UPDATE menu_item_images SET item_id = ?, image_url = ? WHERE image_id = ?";

    private static final String DELETE_IMAGE =
            "DELETE FROM menu_item_images WHERE image_id = ?";

    @Override
    public int addImage(MenuItemImage image) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(INSERT_IMAGE)) {

            pstmt.setInt(1, image.getItemId());
            pstmt.setString(2, image.getImageUrl());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public MenuItemImage getImageById(int imageId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_IMAGE_BY_ID)) {

            pstmt.setInt(1, imageId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractImage(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<MenuItemImage> getImagesByItemId(int itemId) {

        List<MenuItemImage> imageList = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_IMAGES_BY_ITEM_ID)) {

            pstmt.setInt(1, itemId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                imageList.add(extractImage(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return imageList;
    }

    @Override
    public List<MenuItemImage> getAllImages() {

        List<MenuItemImage> imageList = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_ALL_IMAGES)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                imageList.add(extractImage(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return imageList;
    }

    @Override
    public int updateImage(MenuItemImage image) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(UPDATE_IMAGE)) {

            pstmt.setInt(1, image.getItemId());
            pstmt.setString(2, image.getImageUrl());
            pstmt.setInt(3, image.getImageId());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int deleteImage(int imageId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(DELETE_IMAGE)) {

            pstmt.setInt(1, imageId);

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private MenuItemImage extractImage(ResultSet rs) throws SQLException {

        return new MenuItemImage(

                rs.getInt("image_id"),
                rs.getInt("item_id"),
                rs.getString("image_url"),
                rs.getTimestamp("uploaded_at")

        );
    }

}