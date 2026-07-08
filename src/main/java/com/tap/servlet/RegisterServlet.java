package com.tap.servlet;

import java.io.IOException;

import com.tap.DAO.UserDAO;
import com.tap.DAOImpl.UserDAOImpl;
import com.tap.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        User user = new User();

        user.setFirstName(request.getParameter("firstName"));
        user.setLastName(request.getParameter("lastName"));
        user.setEmail(request.getParameter("email"));
        user.setPasswordHash(request.getParameter("password"));
        user.setPhoneNumber(request.getParameter("phone"));

        user.setRole("CUSTOMER");
        user.setProfileImage(null);
        user.setVerified(false);
        user.setAccountStatus("ACTIVE");

        UserDAO dao = new UserDAOImpl();

        int result = dao.addUser(user);

        if(result > 0){

            response.sendRedirect("login.jsp");

        }
        else{

            response.sendRedirect("register.jsp");

        }

    }

}