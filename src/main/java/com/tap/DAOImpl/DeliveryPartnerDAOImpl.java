package com.tap.DAOImpl;

import com.tap.DAO.DeliveryPartnerDAO;
import com.tap.model.DeliveryPartner;
import com.tap.utility.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeliveryPartnerDAOImpl implements DeliveryPartnerDAO {

    private static final String INSERT =
            "INSERT INTO delivery_partners(first_name,last_name,email,phone_number,vehicle_type,vehicle_number,license_number,profile_image,availability_status) VALUES (?,?,?,?,?,?,?,?,?)";

    private static final String SELECT_BY_ID =
            "SELECT * FROM delivery_partners WHERE partner_id=?";

    private static final String SELECT_ALL =
            "SELECT * FROM delivery_partners";

    private static final String SELECT_AVAILABLE =
            "SELECT * FROM delivery_partners WHERE availability_status='AVAILABLE'";

    private static final String UPDATE =
            "UPDATE delivery_partners SET first_name=?,last_name=?,email=?,phone_number=?,vehicle_type=?,vehicle_number=?,license_number=?,profile_image=?,availability_status=? WHERE partner_id=?";

    private static final String DELETE =
            "DELETE FROM delivery_partners WHERE partner_id=?";

    @Override
    public int addPartner(DeliveryPartner p) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT)) {

            ps.setString(1, p.getFirstName());
            ps.setString(2, p.getLastName());
            ps.setString(3, p.getEmail());
            ps.setString(4, p.getPhoneNumber());
            ps.setString(5, p.getVehicleType());
            ps.setString(6, p.getVehicleNumber());
            ps.setString(7, p.getLicenseNumber());
            ps.setString(8, p.getProfileImage());
            ps.setString(9, p.getAvailabilityStatus());

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public DeliveryPartner getPartnerById(int id) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_ID)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return map(rs);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<DeliveryPartner> getAllPartners() {

        List<DeliveryPartner> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<DeliveryPartner> getAvailablePartners() {

        List<DeliveryPartner> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_AVAILABLE)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public int updatePartner(DeliveryPartner p) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {

            ps.setString(1, p.getFirstName());
            ps.setString(2, p.getLastName());
            ps.setString(3, p.getEmail());
            ps.setString(4, p.getPhoneNumber());
            ps.setString(5, p.getVehicleType());
            ps.setString(6, p.getVehicleNumber());
            ps.setString(7, p.getLicenseNumber());
            ps.setString(8, p.getProfileImage());
            ps.setString(9, p.getAvailabilityStatus());
            ps.setInt(10, p.getPartnerId());

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int deletePartner(int id) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE)) {

            ps.setInt(1, id);
            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    private DeliveryPartner map(ResultSet rs) throws SQLException {

        DeliveryPartner p = new DeliveryPartner();

        p.setPartnerId(rs.getInt("partner_id"));
        p.setFirstName(rs.getString("first_name"));
        p.setLastName(rs.getString("last_name"));
        p.setEmail(rs.getString("email"));
        p.setPhoneNumber(rs.getString("phone_number"));
        p.setVehicleType(rs.getString("vehicle_type"));
        p.setVehicleNumber(rs.getString("vehicle_number"));
        p.setLicenseNumber(rs.getString("license_number"));
        p.setProfileImage(rs.getString("profile_image"));
        p.setAvailabilityStatus(rs.getString("availability_status"));
        p.setRating(rs.getDouble("rating"));
        p.setTotalDeliveries(rs.getInt("total_deliveries"));
        p.setCreatedAt(rs.getTimestamp("created_at"));
        p.setUpdatedAt(rs.getTimestamp("updated_at"));

        return p;
    }
}