package com.tap.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.List;

import com.tap.DAO.RestaurantDAO;
import com.tap.DAOImpl.RestaurantDAOImpl;
import com.tap.model.Restaurant;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/adminRestaurants")
public class AdminRestaurantServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private RestaurantDAO restaurantDAO;

    @Override
    public void init() throws ServletException {
        restaurantDAO = new RestaurantDAOImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect("adminLogin.jsp");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        String keyword = request.getParameter("keyword");
        List<Restaurant> restaurantList;
        if (keyword != null && !keyword.trim().isEmpty()) {
            restaurantList = restaurantDAO.searchRestaurants(keyword);
        } else {
            restaurantList = restaurantDAO.getAllRestaurants();
        }
        request.setAttribute("restaurantList", restaurantList);

        if ("edit".equalsIgnoreCase(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Restaurant editRestaurant = restaurantDAO.getRestaurantById(id);
                request.setAttribute("editRestaurant", editRestaurant);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("delete".equalsIgnoreCase(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                restaurantDAO.deleteRestaurant(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.sendRedirect("adminRestaurants");
            return;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("adminRestaurants.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect("adminLogin.jsp");
            return;
        }

        String action = request.getParameter("action");
        
        int ownerId = 1;
        try { ownerId = Integer.parseInt(request.getParameter("ownerId")); } catch (Exception e) {}
        
        String restaurantName = request.getParameter("restaurantName");
        String description = request.getParameter("description");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        
        String logo = request.getParameter("logo");
        if (logo != null && logo.trim().isEmpty()) {
            logo = null;
        }
        
        String bannerImage = request.getParameter("bannerImage");
        if (bannerImage != null && bannerImage.trim().isEmpty()) {
            bannerImage = null;
        }
        
        Time openingTime = parseTime(request.getParameter("openingTime"));
        Time closingTime = parseTime(request.getParameter("closingTime"));
        
        int averageDeliveryTime = 30;
        try { averageDeliveryTime = Integer.parseInt(request.getParameter("averageDeliveryTime")); } catch (Exception e) {}
        
        BigDecimal minimumOrderAmount = BigDecimal.ZERO;
        try { minimumOrderAmount = new BigDecimal(request.getParameter("minimumOrderAmount")); } catch (Exception e) {}
        
        BigDecimal deliveryFee = BigDecimal.ZERO;
        try { deliveryFee = new BigDecimal(request.getParameter("deliveryFee")); } catch (Exception e) {}
        
        double rating = 4.0;
        try { rating = Double.parseDouble(request.getParameter("rating")); } catch (Exception e) {}
        
        int totalReviews = 0;
        try { totalReviews = Integer.parseInt(request.getParameter("totalReviews")); } catch (Exception e) {}
        
        boolean isOpen = "true".equalsIgnoreCase(request.getParameter("isOpen")) || "on".equalsIgnoreCase(request.getParameter("isOpen"));
        String status = request.getParameter("status");
        if (status == null || status.trim().isEmpty()) {
            status = "PENDING";
        }

        if ("add".equalsIgnoreCase(action)) {
            Restaurant r = new Restaurant(
                ownerId, restaurantName, description, email, phoneNumber, logo, bannerImage,
                openingTime, closingTime, averageDeliveryTime, minimumOrderAmount, deliveryFee,
                rating, totalReviews, isOpen, status
            );
            restaurantDAO.addRestaurant(r);
        } else if ("update".equalsIgnoreCase(action)) {
            try {
                int restaurantId = Integer.parseInt(request.getParameter("restaurantId"));
                Restaurant r = new Restaurant();
                r.setRestaurantId(restaurantId);
                r.setOwnerId(ownerId);
                r.setRestaurantName(restaurantName);
                r.setDescription(description);
                r.setEmail(email);
                r.setPhoneNumber(phoneNumber);
                r.setLogo(logo);
                r.setBannerImage(bannerImage);
                r.setOpeningTime(openingTime);
                r.setClosingTime(closingTime);
                r.setAverageDeliveryTime(averageDeliveryTime);
                r.setMinimumOrderAmount(minimumOrderAmount);
                r.setDeliveryFee(deliveryFee);
                r.setRating(rating);
                r.setTotalReviews(totalReviews);
                r.setOpen(isOpen);
                r.setStatus(status);
                restaurantDAO.updateRestaurant(r);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect("adminRestaurants");
    }

    private Time parseTime(String timeStr) {
        if (timeStr == null || timeStr.trim().isEmpty()) {
            return Time.valueOf("09:00:00");
        }
        try {
            if (timeStr.length() == 5) {
                timeStr += ":00";
            }
            return Time.valueOf(timeStr);
        } catch (Exception e) {
            e.printStackTrace();
            return Time.valueOf("09:00:00");
        }
    }
}
