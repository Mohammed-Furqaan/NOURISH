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
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAOImpl();

        User user = userDAO.loginUser(email, password);

        if (user != null) {

            HttpSession session = request.getSession();

            session.setAttribute("loggedInUser", user);

            response.sendRedirect("home");

        } else {

            request.setAttribute("error", "Invalid Email or Password");

            request.getRequestDispatcher("login.jsp")
                   .forward(request, response);
        }
    }
}