package com.tap.servlet;

import java.io.IOException;
import java.util.ArrayList;

import com.tap.DAO.CategoryDAO;
import com.tap.DAO.RestaurantDAO;
import com.tap.DAOImpl.CategoryDAOImpl;
import com.tap.DAOImpl.RestaurantDAOImpl;
import com.tap.model.Category;
import com.tap.model.Restaurant;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
	        HttpServletResponse response)
	        throws ServletException, IOException {

	    RestaurantDAO restaurantDAO = new RestaurantDAOImpl();
	    CategoryDAO categoryDAO = new CategoryDAOImpl();

	    ArrayList<Restaurant> restaurantList = (ArrayList<Restaurant>) restaurantDAO.getAllRestaurants();
	    ArrayList<Category> categoryList = (ArrayList<Category>) categoryDAO.getAllCategories();
	    

	    System.out.println("Restaurant Count : " + restaurantList.size());

	    for(Restaurant r : restaurantList){
	        System.out.println(r.getRestaurantName());
	    }

	    System.out.println("Category Count : " + categoryList.size());

	    for(Category c : categoryList){
	        System.out.println(c.getCategoryName());
	    }

	    request.setAttribute("restaurantList", restaurantList);
	    request.setAttribute("categoryList", categoryList);

	    RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
	    rd.forward(request, response);
	}
}
