package com.tap.DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.tap.DAO.UserDAO;
import com.tap.model.User;
import com.tap.utility.DBConnection;

public class UserDAOImpl implements UserDAO {

    private static final String INSERT_USER =
            "INSERT INTO users(first_name,last_name,email,password_hash,phone_number,role,profile_image,is_verified,account_status) VALUES(?,?,?,?,?,?,?,?,?)";

    private static final String GET_USER_BY_ID =
            "SELECT * FROM users WHERE user_id=?";

    private static final String GET_USER_BY_EMAIL =
            "SELECT * FROM users WHERE email=?";

    private static final String GET_ALL_USERS =
            "SELECT * FROM users";

    private static final String UPDATE_USER =
            "UPDATE users SET first_name=?,last_name=?,email=?,phone_number=?,profile_image=?,account_status=? WHERE user_id=?";

    private static final String DELETE_USER =
            "DELETE FROM users WHERE user_id=?";
    
    private static final String LOGIN_USER =
    		"SELECT * FROM users WHERE email=? AND password_hash=?";
    
    private static final String USER_COUNT =
    		"SELECT COUNT(*) FROM users";

    
    @Override
    public User loginUser(String email, String password) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(LOGIN_USER)) {

            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractUser(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int addUser(User user) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(INSERT_USER)) {

            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPasswordHash());
            pstmt.setString(5, user.getPhoneNumber());
            pstmt.setString(6, user.getRole());
            pstmt.setString(7, user.getProfileImage());
            pstmt.setBoolean(8, user.isVerified());
            pstmt.setString(9, user.getAccountStatus());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public User getUserById(int userId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_USER_BY_ID)) {

            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractUser(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public User getUserByEmail(String email) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_USER_BY_EMAIL)) {

            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extractUser(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(GET_ALL_USERS)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                users.add(extractUser(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public int updateUser(User user) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(UPDATE_USER)) {

            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPhoneNumber());
            pstmt.setString(5, user.getProfileImage());
            pstmt.setString(6, user.getAccountStatus());
            pstmt.setInt(7, user.getUserId());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int deleteUser(int userId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(DELETE_USER)) {

            pstmt.setInt(1, userId);

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private User extractUser(ResultSet rs) throws SQLException {

        return new User(

                rs.getInt("user_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("password_hash"),
                rs.getString("phone_number"),
                rs.getString("role"),
                rs.getString("profile_image"),
                rs.getBoolean("is_verified"),
                rs.getString("account_status"),
                rs.getTimestamp("last_login"),
                rs.getTimestamp("created_at"),
                rs.getTimestamp("updated_at")
        );
    }
    
    @Override
    public int getUserCount() {

        try(Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(USER_COUNT)){

            ResultSet rs=ps.executeQuery();

            if(rs.next()){

                return rs.getInt(1);

            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return 0;
    }
}