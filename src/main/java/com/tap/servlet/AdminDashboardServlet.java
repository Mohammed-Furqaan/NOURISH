package com.tap.servlet;

import java.io.IOException;
import java.util.List;

import com.tap.DAO.CategoryDAO;
import com.tap.DAO.MenuItemDAO;
import com.tap.DAO.OrderDAO;
import com.tap.DAO.RestaurantDAO;
import com.tap.DAO.UserDAO;
import com.tap.DAOImpl.CategoryDAOImpl;
import com.tap.DAOImpl.MenuItemDAOImpl;
import com.tap.DAOImpl.OrderDAOImpl;
import com.tap.DAOImpl.RestaurantDAOImpl;
import com.tap.DAOImpl.UserDAOImpl;
import com.tap.model.Admin;
import com.tap.model.Order;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/adminDashboard")
public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;
    private RestaurantDAO restaurantDAO;
    private CategoryDAO categoryDAO;
    private MenuItemDAO menuItemDAO;
    private OrderDAO orderDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAOImpl();
        restaurantDAO = new RestaurantDAOImpl();
        categoryDAO = new CategoryDAOImpl();
        menuItemDAO = new MenuItemDAOImpl();
        orderDAO = new OrderDAOImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect("adminLogin.jsp");
            return;
        }

        int totalUsers = userDAO.getUserCount();
        int totalRestaurants = restaurantDAO.getRestaurantCount();
        int totalCategories = categoryDAO.getCategoryCount();
        int totalMenuItems = menuItemDAO.getMenuItemCount();
        int totalOrders = orderDAO.getOrderCount();
        double totalRevenue = orderDAO.getTotalRevenue();

        List<Order> allOrders = orderDAO.getAllOrders();
        // Sort in memory by orderId descending to get the latest orders
        allOrders.sort((o1, o2) -> Integer.compare(o2.getOrderId(), o1.getOrderId()));
        List<Order> latestOrders = allOrders.subList(0, Math.min(5, allOrders.size()));

        request.setAttribute("totalUsers", totalUsers);
        request.setAttribute("totalRestaurants", totalRestaurants);
        request.setAttribute("totalCategories", totalCategories);
        request.setAttribute("totalMenuItems", totalMenuItems);
        request.setAttribute("totalOrders", totalOrders);
        request.setAttribute("totalRevenue", totalRevenue);
        request.setAttribute("latestOrders", latestOrders);

        RequestDispatcher dispatcher = request.getRequestDispatcher("adminDashboard.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
