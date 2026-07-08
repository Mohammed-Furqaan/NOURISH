package com.tap.DAOImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.tap.DAO.FavoriteDAO;
import com.tap.model.Favorite;
import com.tap.utility.DBConnection;

public class FavoriteDAOImpl implements FavoriteDAO {

    private static final String INSERT =
            "INSERT INTO favorites(user_id, restaurant_id) VALUES (?, ?)";

    private static final String GET_BY_ID =
            "SELECT * FROM favorites WHERE favorite_id=?";

    private static final String GET_BY_USER =
            "SELECT * FROM favorites WHERE user_id=?";

    private static final String GET_ALL =
            "SELECT * FROM favorites";

    private static final String DELETE =
            "DELETE FROM favorites WHERE favorite_id=?";
    
    private static final String DELETE_BY_USER_AND_RESTAURANT =
            "DELETE FROM favorites WHERE user_id=? AND restaurant_id=?";
    
    @Override
    public int deleteFavoriteByUserAndRestaurant(int userId, int restaurantId) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(DELETE_BY_USER_AND_RESTAURANT)) {

            ps.setInt(1, userId);
            ps.setInt(2, restaurantId);

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int addFavorite(Favorite f) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT)) {

            ps.setInt(1, f.getUserId());
            ps.setInt(2, f.getRestaurantId());

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public Favorite getFavoriteById(int id) {

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
    public List<Favorite> getFavoritesByUserId(int userId) {

        List<Favorite> list = new ArrayList<>();

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
    public List<Favorite> getAllFavorites() {

        List<Favorite> list = new ArrayList<>();

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
    public int deleteFavorite(int id) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE)) {

            ps.setInt(1, id);

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    private Favorite extract(ResultSet rs) throws SQLException {

        return new Favorite(
                rs.getInt("favorite_id"),
                rs.getInt("user_id"),
                rs.getInt("restaurant_id"),
                rs.getTimestamp("created_at")
        );
    }
}