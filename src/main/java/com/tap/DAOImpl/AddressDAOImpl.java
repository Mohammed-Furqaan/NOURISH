package com.tap.DAOImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.tap.DAO.AddressDAO;
import com.tap.model.UserAddress;
import com.tap.utility.DBConnection;

public class AddressDAOImpl implements AddressDAO {

    private static final String INSERT_ADDRESS =
            "INSERT INTO user_address(user_id,address_type,house_no,street,area,landmark,city,state,pincode,latitude,longitude,is_default) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String GET_ADDRESS_BY_ID =
            "SELECT * FROM user_address WHERE address_id=?";

    private static final String GET_ADDRESSES_BY_USER =
            "SELECT * FROM user_address WHERE user_id=?";

    private static final String GET_ALL_ADDRESS =
            "SELECT * FROM user_address";

    private static final String UPDATE_ADDRESS =
            "UPDATE user_address SET address_type=?,house_no=?,street=?,area=?,landmark=?,city=?,state=?,pincode=?,latitude=?,longitude=?,is_default=? WHERE address_id=?";

    private static final String DELETE_ADDRESS =
            "DELETE FROM user_address WHERE address_id=?";

    @Override
    public int addAddress(UserAddress address) {

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(INSERT_ADDRESS)) {

            pstmt.setInt(1, address.getUserId());
            pstmt.setString(2, address.getAddressType());
            pstmt.setString(3, address.getHouseNo());
            pstmt.setString(4, address.getStreet());
            pstmt.setString(5, address.getArea());
            pstmt.setString(6, address.getLandmark());
            pstmt.setString(7, address.getCity());
            pstmt.setString(8, address.getState());
            pstmt.setString(9, address.getPincode());
            pstmt.setDouble(10, address.getLatitude());
            pstmt.setDouble(11, address.getLongitude());
            pstmt.setBoolean(12, address.isDefault());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public UserAddress getAddressById(int addressId) {

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(GET_ADDRESS_BY_ID)) {

            pstmt.setInt(1, addressId);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                return extractAddress(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<UserAddress> getAddressesByUserId(int userId) {

        List<UserAddress> addressList = new ArrayList<>();

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(GET_ADDRESSES_BY_USER)) {

            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                addressList.add(extractAddress(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return addressList;
    }

    @Override
    public List<UserAddress> getAllAddresses() {

        List<UserAddress> addressList = new ArrayList<>();

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(GET_ALL_ADDRESS)) {

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                addressList.add(extractAddress(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return addressList;
    }

    @Override
    public int updateAddress(UserAddress address) {

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(UPDATE_ADDRESS)) {

            pstmt.setString(1, address.getAddressType());
            pstmt.setString(2, address.getHouseNo());
            pstmt.setString(3, address.getStreet());
            pstmt.setString(4, address.getArea());
            pstmt.setString(5, address.getLandmark());
            pstmt.setString(6, address.getCity());
            pstmt.setString(7, address.getState());
            pstmt.setString(8, address.getPincode());
            pstmt.setDouble(9, address.getLatitude());
            pstmt.setDouble(10, address.getLongitude());
            pstmt.setBoolean(11, address.isDefault());
            pstmt.setInt(12, address.getAddressId());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int deleteAddress(int addressId) {

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(DELETE_ADDRESS)) {

            pstmt.setInt(1, addressId);

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private UserAddress extractAddress(ResultSet rs) throws SQLException {

        return new UserAddress(

                rs.getInt("address_id"),
                rs.getInt("user_id"),
                rs.getString("address_type"),
                rs.getString("house_no"),
                rs.getString("street"),
                rs.getString("area"),
                rs.getString("landmark"),
                rs.getString("city"),
                rs.getString("state"),
                rs.getString("pincode"),
                rs.getDouble("latitude"),
                rs.getDouble("longitude"),
                rs.getBoolean("is_default"),
                rs.getTimestamp("created_at")

        );
    }

}