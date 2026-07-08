package com.tap.DAO;

import java.util.List;
import com.tap.model.Admin;

public interface AdminDAO {

    int addAdmin(Admin admin);

    Admin getAdminById(int adminId);

    Admin getAdminByEmail(String email);

    List<Admin> getAllAdmins();

    int updateAdmin(Admin admin);

    int deleteAdmin(int adminId);
    
    Admin loginAdmin(String email, String password);
}