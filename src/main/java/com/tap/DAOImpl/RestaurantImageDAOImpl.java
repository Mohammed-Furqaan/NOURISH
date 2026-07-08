package com.tap.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tap.DAO.RestaurantImageDAO;
import com.tap.model.RestaurantImage;
import com.tap.utility.DBConnection;

public class RestaurantImageDAOImpl implements RestaurantImageDAO {

    private static final String INSERT_IMAGE =
            "INSERT INTO restaurant_images (restaurant_id, image_url, image_type) VALUES (?, ?, ?)";

    private static final String GET_IMAGE_BY_ID =
            "SELECT * FROM restaurant_images WHERE image_id = ?";

    private static final String GET_IMAGES_BY_RESTAURANT_ID =
            "SELECT * FROM restaurant_images WHERE restaurant_id = ?";

    private static final String GET_ALL_IMAGES =
            "SELECT * FROM restaurant_images";

    private static final String UPDATE_IMAGE =
            "UPDATE restaurant_images SET restaurant_id = ?, image_url = ?, image_type = ? WHERE image_id = ?";

    private static final String DELETE_IMAGE =
            "DELETE FROM restaurant_images WHERE image_id = ?";

    @Override
    public int addImage(RestaurantImage image) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(INSERT_IMAGE)) {

            pstmt.setInt(1, image.getRestaurantId());
            pstmt.setString(2, image.getImageUrl());
            pstmt.setString(3, image.getImageType());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public RestaurantImage getImageById(int imageId) {

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
    public List<RestaurantImage> getImagesByRestaurantId(int restaurantId) {

        List<RestaurantImage> imageList = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_IMAGES_BY_RESTAURANT_ID)) {

            pstmt.setInt(1, restaurantId);

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
    public List<RestaurantImage> getAllImages() {

        List<RestaurantImage> imageList = new ArrayList<>();

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
    public int updateImage(RestaurantImage image) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(UPDATE_IMAGE)) {

            pstmt.setInt(1, image.getRestaurantId());
            pstmt.setString(2, image.getImageUrl());
            pstmt.setString(3, image.getImageType());
            pstmt.setInt(4, image.getImageId());

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

    private RestaurantImage extractImage(ResultSet rs) throws SQLException {

        return new RestaurantImage(

                rs.getInt("image_id"),
                rs.getInt("restaurant_id"),
                rs.getString("image_url"),
                rs.getString("image_type"),
                rs.getTimestamp("uploaded_at")

        );
    }

}