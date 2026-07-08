package com.tap.servlet;

import java.io.IOException;
import java.util.ArrayList;

import com.tap.DAO.RestaurantDAO;
import com.tap.DAOImpl.RestaurantDAOImpl;
import com.tap.model.Restaurant;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");

        RestaurantDAO dao = new RestaurantDAOImpl();

        ArrayList<Restaurant> restaurantList =
                (ArrayList<Restaurant>) dao.searchRestaurants(keyword);

        request.setAttribute("restaurantList", restaurantList);
        request.setAttribute("keyword", keyword);

        request.getRequestDispatcher("search.jsp")
               .forward(request, response);

    }
}