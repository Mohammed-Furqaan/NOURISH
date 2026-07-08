package com.tap.DAOImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.tap.DAO.ReviewDAO;
import com.tap.model.Review;
import com.tap.utility.DBConnection;

public class ReviewDAOImpl implements ReviewDAO {

    private static final String INSERT =
            "INSERT INTO reviews(user_id, restaurant_id, order_id, rating, review_text) VALUES (?, ?, ?, ?, ?)";

    private static final String GET_BY_ID =
            "SELECT * FROM reviews WHERE review_id=?";

    private static final String GET_BY_RESTAURANT =
            "SELECT * FROM reviews WHERE restaurant_id=?";

    private static final String GET_BY_USER =
            "SELECT * FROM reviews WHERE user_id=?";

    private static final String GET_ALL =
            "SELECT * FROM reviews";

    private static final String UPDATE =
            "UPDATE reviews SET user_id=?, restaurant_id=?, order_id=?, rating=?, review_text=? WHERE review_id=?";

    private static final String DELETE =
            "DELETE FROM reviews WHERE review_id=?";

    @Override
    public int addReview(Review r) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT)) {

            ps.setInt(1, r.getUserId());
            ps.setInt(2, r.getRestaurantId());
            ps.setInt(3, r.getOrderId());
            ps.setInt(4, r.getRating());
            ps.setString(5, r.getReviewText());

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public Review getReviewById(int id) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_BY_ID)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extract(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Review> getReviewsByRestaurantId(int restaurantId) {

        List<Review> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_BY_RESTAURANT)) {

            ps.setInt(1, restaurantId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(extract(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<Review> getReviewsByUserId(int userId) {

        List<Review> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_BY_USER)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(extract(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<Review> getAllReviews() {

        List<Review> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_ALL)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(extract(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public int updateReview(Review r) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {

            ps.setInt(1, r.getUserId());
            ps.setInt(2, r.getRestaurantId());
            ps.setInt(3, r.getOrderId());
            ps.setInt(4, r.getRating());
            ps.setString(5, r.getReviewText());
            ps.setInt(6, r.getReviewId());

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int deleteReview(int reviewId) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE)) {

            ps.setInt(1, reviewId);

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    private Review extract(ResultSet rs) throws SQLException {

        return new Review(
                rs.getInt("review_id"),
                rs.getInt("user_id"),
                rs.getInt("restaurant_id"),
                rs.getInt("order_id"),
                rs.getInt("rating"),
                rs.getString("review_text"),
                rs.getTimestamp("created_at")
        );
    }
}