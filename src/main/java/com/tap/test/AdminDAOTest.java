package com.tap.test;

import com.tap.DAO.AdminDAO;
import com.tap.DAOImpl.AdminDAOImpl;
import com.tap.model.Admin;

public class AdminDAOTest {

    public static void main(String[] args) {

        AdminDAO dao = new AdminDAOImpl();

        Admin admin = new Admin(
                "Mohammed",
                "Furqaan",
                "admin@nourish.com",
                "hashed123",
                "9876543210",
                null,
                "SUPER_ADMIN",
                "ACTIVE"
        );

        int result = dao.addAdmin(admin);

        if (result > 0) {
            System.out.println("Admin Added Successfully...");
        } else {
            System.out.println("Failed to Add Admin...");
        }
    }
}