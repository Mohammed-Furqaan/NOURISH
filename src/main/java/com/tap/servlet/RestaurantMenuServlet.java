package com.tap.servlet;

import java.io.IOException;
import java.util.ArrayList;

import com.tap.DAO.MenuItemDAO;
import com.tap.DAO.RestaurantDAO;
import com.tap.DAOImpl.MenuItemDAOImpl;
import com.tap.DAOImpl.RestaurantDAOImpl;
import com.tap.model.MenuItem;
import com.tap.model.Restaurant;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/restaurantMenu")
public class RestaurantMenuServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int restaurantId =
                Integer.parseInt(request.getParameter("restaurantId"));

        RestaurantDAO restaurantDAO = new RestaurantDAOImpl();
        MenuItemDAO menuItemDAO = new MenuItemDAOImpl();

        Restaurant restaurant =
                restaurantDAO.getRestaurantById(restaurantId);

        ArrayList<MenuItem> menuList =
                (ArrayList<MenuItem>) menuItemDAO.getMenuItemsByRestaurantId(restaurantId);

        request.setAttribute("restaurant", restaurant);
        request.setAttribute("menuList", menuList);

        RequestDispatcher rd =
                request.getRequestDispatcher("restaurant-menu.jsp");

        rd.forward(request, response);
    }
}