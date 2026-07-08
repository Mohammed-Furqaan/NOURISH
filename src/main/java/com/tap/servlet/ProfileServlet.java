package com.tap.servlet;

import java.io.IOException;

import com.tap.DAO.UserDAO;
import com.tap.DAOImpl.UserDAOImpl;
import com.tap.model.User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loggedInUser") == null) {

            response.sendRedirect("login.jsp");
            return;
        }

        User loggedInUser =
                (User) session.getAttribute("loggedInUser");

        UserDAO userDAO = new UserDAOImpl();

        User user =
                userDAO.getUserById(loggedInUser.getUserId());

        request.setAttribute("user", user);

        RequestDispatcher rd =
                request.getRequestDispatcher("profile.jsp");

        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loggedInUser") == null) {

            response.sendRedirect("login.jsp");
            return;
        }

        User loggedInUser =
                (User) session.getAttribute("loggedInUser");

        UserDAO userDAO = new UserDAOImpl();

        User user =
                userDAO.getUserById(loggedInUser.getUserId());

        user.setFirstName(request.getParameter("firstName"));
        user.setLastName(request.getParameter("lastName"));
        user.setEmail(request.getParameter("email"));
        user.setPhoneNumber(request.getParameter("phone"));

        userDAO.updateUser(user);

        session.setAttribute("loggedInUser", user);

        response.sendRedirect("profile");
    }
}