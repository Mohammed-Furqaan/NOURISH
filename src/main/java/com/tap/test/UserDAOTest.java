package com.tap.test;

import com.tap.DAO.UserDAO;
import com.tap.DAOImpl.UserDAOImpl;
import com.tap.model.User;

public class UserDAOTest {

    public static void main(String[] args) {

        UserDAO dao = new UserDAOImpl();

        User user = dao.getUserById(1);

        if(user != null) {
            System.out.println(user);
        }
        else {
            System.out.println("User Not Found");
        }
    }
}