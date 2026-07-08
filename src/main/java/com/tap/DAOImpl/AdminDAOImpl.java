package com.tap.DAOImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.tap.DAO.AdminDAO;
import com.tap.model.Admin;
import com.tap.utility.DBConnection;

public class AdminDAOImpl implements AdminDAO {

    private static final String INSERT =
            "INSERT INTO admins(first_name, last_name, email, password_hash, phone_number, profile_image, role, account_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String GET_BY_ID =
            "SELECT * FROM admins WHERE admin_id=?";

    private static final String GET_BY_EMAIL =
            "SELECT * FROM admins WHERE email=?";

    private static final String GET_ALL =
            "SELECT * FROM admins";

    private static final String UPDATE =
            "UPDATE admins SET first_name=?, last_name=?, email=?, password_hash=?, phone_number=?, profile_image=?, role=?, account_status=? WHERE admin_id=?";

    private static final String DELETE =
            "DELETE FROM admins WHERE admin_id=?";
    
    private static final String LOGIN_ADMIN =
    		"SELECT * FROM admins WHERE email=? AND password_hash=?";

    @Override
    public int addAdmin(Admin a) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT)) {

            ps.setString(1, a.getFirstName());
            ps.setString(2, a.getLastName());
            ps.setString(3, a.getEmail());
            ps.setString(4, a.getPasswordHash());
            ps.setString(5, a.getPhoneNumber());
            ps.setString(6, a.getProfileImage());
            ps.setString(7, a.getRole());
            ps.setString(8, a.getAccountStatus());

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public Admin getAdminById(int id) {

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
    public Admin getAdminByEmail(String email) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_BY_EMAIL)) {

            ps.setString(1, email);

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
    public List<Admin> getAllAdmins() {

        List<Admin> list = new ArrayList<>();

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
    public int updateAdmin(Admin a) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {

            ps.setString(1, a.getFirstName());
            ps.setString(2, a.getLastName());
            ps.setString(3, a.getEmail());
            ps.setString(4, a.getPasswordHash());
            ps.setString(5, a.getPhoneNumber());
            ps.setString(6, a.getProfileImage());
            ps.setString(7, a.getRole());
            ps.setString(8, a.getAccountStatus());
            ps.setInt(9, a.getAdminId());

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int deleteAdmin(int id) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE)) {

            ps.setInt(1, id);

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    private Admin extract(ResultSet rs) throws SQLException {

        return new Admin(
                rs.getInt("admin_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("password_hash"),
                rs.getString("phone_number"),
                rs.getString("profile_image"),
                rs.getString("role"),
                rs.getString("account_status"),
                rs.getTimestamp("last_login"),
                rs.getTimestamp("created_at"),
                rs.getTimestamp("updated_at")
        );
    }
    
    @Override
    public Admin loginAdmin(String email, String password) {
    	
    	System.out.println("Inside loginAdmin()");
        System.out.println("Email = " + email);
        System.out.println("Password = " + password);
        
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(LOGIN_ADMIN)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            System.out.println("Executed Query");

            if(rs.next()) {
                System.out.println("FOUND");
                return extract(rs);
            }

            System.out.println("NOT FOUND");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}