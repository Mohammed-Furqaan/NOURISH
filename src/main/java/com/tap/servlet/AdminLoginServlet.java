package com.tap.servlet;

import java.io.IOException;
import com.tap.DAO.AdminDAO;
import com.tap.DAOImpl.AdminDAOImpl;
import com.tap.model.Admin;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/adminLogin")
public class AdminLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AdminDAO adminDAO;

    @Override
    public void init() throws ServletException {
        adminDAO = new AdminDAOImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // If already logged in, redirect to dashboard
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("admin") != null) {
            response.sendRedirect("adminDashboard");
            return;
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("adminLogin.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Admin admin = adminDAO.loginAdmin(email, password);

        if (admin != null) {
            if ("ACTIVE".equalsIgnoreCase(admin.getAccountStatus())) {
                HttpSession session = request.getSession();
                session.setAttribute("admin", admin);
                response.sendRedirect("adminDashboard");
            } else {
                request.setAttribute("error", "Your account is inactive. Please contact system administrator.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("adminLogin.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            request.setAttribute("error", "Invalid Email or Password!");
            RequestDispatcher dispatcher = request.getRequestDispatcher("adminLogin.jsp");
            dispatcher.forward(request, response);
        }
    }
}
