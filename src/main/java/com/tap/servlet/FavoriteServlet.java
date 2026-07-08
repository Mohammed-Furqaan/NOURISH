package com.tap.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.tap.DAO.FavoriteDAO;
import com.tap.DAO.RestaurantDAO;
import com.tap.DAOImpl.FavoriteDAOImpl;
import com.tap.DAOImpl.RestaurantDAOImpl;
import com.tap.model.Favorite;
import com.tap.model.Restaurant;
import com.tap.model.User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/favorites")
public class FavoriteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if(session == null ||
                session.getAttribute("loggedInUser")==null){

            response.sendRedirect("login.jsp");
            return;
        }

        User user =
                (User)session.getAttribute("loggedInUser");

        int userId = user.getUserId();

        FavoriteDAO favoriteDAO = new FavoriteDAOImpl();
        RestaurantDAO restaurantDAO = new RestaurantDAOImpl();

        String action = request.getParameter("action");

        if("remove".equals(action)){

            int restaurantId =
                    Integer.parseInt(request.getParameter("restaurantId"));

            favoriteDAO.deleteFavoriteByUserAndRestaurant(
                    userId,
                    restaurantId);

            response.sendRedirect("favorites");
            return;
        }

        List<Favorite> favorites =
                favoriteDAO.getFavoritesByUserId(userId);

        List<Restaurant> restaurants =
                new ArrayList<>();

        for(Favorite favorite : favorites){

            restaurants.add(

                    restaurantDAO.getRestaurantById(
                            favorite.getRestaurantId())

            );
        }

        request.setAttribute(
                "restaurantList",
                restaurants);

        RequestDispatcher rd =
                request.getRequestDispatcher(
                        "favorites.jsp");

        rd.forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if(session == null ||
                session.getAttribute("loggedInUser")==null){

            response.sendRedirect("login.jsp");
            return;
        }

        User user =
                (User)session.getAttribute("loggedInUser");

        int userId = user.getUserId();

        int restaurantId =
                Integer.parseInt(
                        request.getParameter("restaurantId"));

        Favorite favorite = new Favorite();

        favorite.setUserId(userId);
        favorite.setRestaurantId(restaurantId);

        FavoriteDAO favoriteDAO =
                new FavoriteDAOImpl();

        favoriteDAO.addFavorite(favorite);

        response.sendRedirect("favorites");
    }
}