package com.tap.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.tap.DAO.CategoryDAO;
import com.tap.DAO.MenuItemDAO;
import com.tap.DAO.RestaurantDAO;
import com.tap.DAOImpl.CategoryDAOImpl;
import com.tap.DAOImpl.MenuItemDAOImpl;
import com.tap.DAOImpl.RestaurantDAOImpl;
import com.tap.model.Category;
import com.tap.model.MenuItem;
import com.tap.model.Restaurant;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/adminMenu")
public class AdminMenuServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private MenuItemDAO menuItemDAO;
    private RestaurantDAO restaurantDAO;
    private CategoryDAO categoryDAO;

    @Override
    public void init() throws ServletException {
        menuItemDAO = new MenuItemDAOImpl();
        restaurantDAO = new RestaurantDAOImpl();
        categoryDAO = new CategoryDAOImpl();
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

        // Fetch filter options
        List<Restaurant> restaurantList = restaurantDAO.getAllRestaurants();
        List<Category> categoryList = categoryDAO.getAllCategories();
        request.setAttribute("restaurantList", restaurantList);
        request.setAttribute("categoryList", categoryList);

        // Fetch and filter menu items
        String keyword = request.getParameter("keyword");
        String filterRestaurantId = request.getParameter("filterRestaurantId");
        String filterCategoryId = request.getParameter("filterCategoryId");

        List<MenuItem> menuItemList = menuItemDAO.getAllMenuItems();

        if (keyword != null && !keyword.trim().isEmpty()) {
            final String kw = keyword.toLowerCase().trim();
            menuItemList = menuItemList.stream()
                .filter(item -> item.getItemName().toLowerCase().contains(kw) || 
                                (item.getDescription() != null && item.getDescription().toLowerCase().contains(kw)))
                .collect(Collectors.toList());
        }

        if (filterRestaurantId != null && !filterRestaurantId.trim().isEmpty() && !"all".equalsIgnoreCase(filterRestaurantId)) {
            try {
                int rId = Integer.parseInt(filterRestaurantId);
                menuItemList = menuItemList.stream()
                    .filter(item -> item.getRestaurantId() == rId)
                    .collect(Collectors.toList());
            } catch (Exception e) {}
        }

        if (filterCategoryId != null && !filterCategoryId.trim().isEmpty() && !"all".equalsIgnoreCase(filterCategoryId)) {
            try {
                int cId = Integer.parseInt(filterCategoryId);
                menuItemList = menuItemList.stream()
                    .filter(item -> item.getCategoryId() == cId)
                    .collect(Collectors.toList());
            } catch (Exception e) {}
        }

        request.setAttribute("menuItemList", menuItemList);

        if ("edit".equalsIgnoreCase(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                MenuItem editMenuItem = menuItemDAO.getMenuItemById(id);
                request.setAttribute("editMenuItem", editMenuItem);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("delete".equalsIgnoreCase(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                menuItemDAO.deleteMenuItem(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.sendRedirect("adminMenu");
            return;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("adminMenu.jsp");
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
        
        int restaurantId = 1;
        try { restaurantId = Integer.parseInt(request.getParameter("restaurantId")); } catch (Exception e) {}
        
        int categoryId = 1;
        try { categoryId = Integer.parseInt(request.getParameter("categoryId")); } catch (Exception e) {}
        
        String itemName = request.getParameter("itemName");
        String description = request.getParameter("description");
        
        BigDecimal price = BigDecimal.ZERO;
        try { price = new BigDecimal(request.getParameter("price")); } catch (Exception e) {}
        
        BigDecimal discountPrice = null;
        try {
            String dp = request.getParameter("discountPrice");
            if (dp != null && !dp.trim().isEmpty()) {
                discountPrice = new BigDecimal(dp);
            }
        } catch (Exception e) {}
        
        String foodType = request.getParameter("foodType");
        
        int preparationTime = 15;
        try { preparationTime = Integer.parseInt(request.getParameter("preparationTime")); } catch (Exception e) {}
        
        Integer calories = null;
        String calStr = request.getParameter("calories");
        if (calStr != null && !calStr.trim().isEmpty()) {
            try { calories = Integer.parseInt(calStr); } catch (Exception e) {}
        }
        
        int quantityAvailable = 100;
        try { quantityAvailable = Integer.parseInt(request.getParameter("quantityAvailable")); } catch (Exception e) {}
        
        String imageUrl = request.getParameter("imageUrl");
        if (imageUrl != null && imageUrl.trim().isEmpty()) {
            imageUrl = null;
        }
        
        BigDecimal rating = BigDecimal.valueOf(4.0);
        try { rating = new BigDecimal(request.getParameter("rating")); } catch (Exception e) {}
        
        int totalReviews = 0;
        try { totalReviews = Integer.parseInt(request.getParameter("totalReviews")); } catch (Exception e) {}
        
        boolean isAvailable = "true".equalsIgnoreCase(request.getParameter("isAvailable")) || "on".equalsIgnoreCase(request.getParameter("isAvailable"));

        if ("add".equalsIgnoreCase(action)) {
            MenuItem m = new MenuItem(
                restaurantId, categoryId, itemName, description, price, discountPrice,
                foodType, preparationTime, calories, quantityAvailable, imageUrl, rating,
                totalReviews, isAvailable
            );
            menuItemDAO.addMenuItem(m);
        } else if ("update".equalsIgnoreCase(action)) {
            try {
                int itemId = Integer.parseInt(request.getParameter("itemId"));
                MenuItem m = new MenuItem();
                m.setItemId(itemId);
                m.setRestaurantId(restaurantId);
                m.setCategoryId(categoryId);
                m.setItemName(itemName);
                m.setDescription(description);
                m.setPrice(price);
                m.setDiscountPrice(discountPrice);
                m.setFoodType(foodType);
                m.setPreparationTime(preparationTime);
                m.setCalories(calories);
                m.setQuantityAvailable(quantityAvailable);
                m.setImageUrl(imageUrl);
                m.setRating(rating);
                m.setTotalReviews(totalReviews);
                m.setAvailable(isAvailable);
                
                menuItemDAO.updateMenuItem(m);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect("adminMenu");
    }
}
