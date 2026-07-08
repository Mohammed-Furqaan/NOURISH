package com.tap.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

import com.tap.DAO.RestaurantAddressDAO;
import com.tap.model.RestaurantAddress;
import com.tap.utility.DBConnection;

public class RestaurantAddressDAOImpl implements RestaurantAddressDAO {

    private static final String INSERT_ADDRESS =
            "INSERT INTO restaurant_address(restaurant_id,building_name,street,area,landmark,city,state,pincode,latitude,longitude) VALUES(?,?,?,?,?,?,?,?,?,?)";

    private static final String GET_ADDRESS_BY_ID =
            "SELECT * FROM restaurant_address WHERE address_id=?";

    private static final String GET_ADDRESS_BY_RESTAURANT_ID =
            "SELECT * FROM restaurant_address WHERE restaurant_id=?";

    private static final String GET_ALL_ADDRESSES =
            "SELECT * FROM restaurant_address";

    private static final String UPDATE_ADDRESS =
            "UPDATE restaurant_address SET building_name=?,street=?,area=?,landmark=?,city=?,state=?,pincode=?,latitude=?,longitude=? WHERE address_id=?";

    private static final String DELETE_ADDRESS =
            "DELETE FROM restaurant_address WHERE address_id=?";

    @Override
    public int addAddress(RestaurantAddress address) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(INSERT_ADDRESS)) {

            pstmt.setInt(1, address.getRestaurantId());
            pstmt.setString(2, address.getBuildingName());
            pstmt.setString(3, address.getStreet());
            pstmt.setString(4, address.getArea());
            pstmt.setString(5, address.getLandmark());
            pstmt.setString(6, address.getCity());
            pstmt.setString(7, address.getState());
            pstmt.setString(8, address.getPincode());
            pstmt.setBigDecimal(9, address.getLatitude());
            pstmt.setBigDecimal(10, address.getLongitude());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public RestaurantAddress getAddressById(int addressId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_ADDRESS_BY_ID)) {

            pstmt.setInt(1, addressId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractAddress(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<RestaurantAddress> getAddressByRestaurantId(int restaurantId) {

        List<RestaurantAddress> addressList = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_ADDRESS_BY_RESTAURANT_ID)) {

            pstmt.setInt(1, restaurantId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                addressList.add(extractAddress(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return addressList;
    }

    @Override
    public List<RestaurantAddress> getAllAddresses() {

        List<RestaurantAddress> addressList = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_ALL_ADDRESSES)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                addressList.add(extractAddress(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return addressList;
    }

    @Override
    public int updateAddress(RestaurantAddress address) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(UPDATE_ADDRESS)) {

            pstmt.setString(1, address.getBuildingName());
            pstmt.setString(2, address.getStreet());
            pstmt.setString(3, address.getArea());
            pstmt.setString(4, address.getLandmark());
            pstmt.setString(5, address.getCity());
            pstmt.setString(6, address.getState());
            pstmt.setString(7, address.getPincode());
            pstmt.setBigDecimal(8, address.getLatitude());
            pstmt.setBigDecimal(9, address.getLongitude());
            pstmt.setInt(10, address.getAddressId());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int deleteAddress(int addressId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(DELETE_ADDRESS)) {

            pstmt.setInt(1, addressId);

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private RestaurantAddress extractAddress(ResultSet rs) throws SQLException {

        return new RestaurantAddress(

                rs.getInt("address_id"),
                rs.getInt("restaurant_id"),
                rs.getString("building_name"),
                rs.getString("street"),
                rs.getString("area"),
                rs.getString("landmark"),
                rs.getString("city"),
                rs.getString("state"),
                rs.getString("pincode"),
                rs.getBigDecimal("latitude"),
                rs.getBigDecimal("longitude"),
                rs.getTimestamp("created_at")

        );
    }
}