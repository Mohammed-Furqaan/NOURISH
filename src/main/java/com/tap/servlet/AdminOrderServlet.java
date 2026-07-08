package com.tap.servlet;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.tap.DAO.OrderDAO;
import com.tap.DAO.OrderItemDAO;
import com.tap.DAO.RestaurantDAO;
import com.tap.DAO.UserDAO;
import com.tap.DAO.MenuItemDAO;
import com.tap.DAOImpl.OrderDAOImpl;
import com.tap.DAOImpl.OrderItemDAOImpl;
import com.tap.DAOImpl.RestaurantDAOImpl;
import com.tap.DAOImpl.UserDAOImpl;
import com.tap.DAOImpl.MenuItemDAOImpl;
import com.tap.model.Order;
import com.tap.model.OrderItem;
import com.tap.model.Restaurant;
import com.tap.model.User;
import com.tap.model.MenuItem;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/adminOrders")
public class AdminOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private OrderDAO orderDAO;
    private OrderItemDAO orderItemDAO;
    private RestaurantDAO restaurantDAO;
    private UserDAO userDAO;
    private MenuItemDAO menuItemDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAOImpl();
        orderItemDAO = new OrderItemDAOImpl();
        restaurantDAO = new RestaurantDAOImpl();
        userDAO = new UserDAOImpl();
        menuItemDAO = new MenuItemDAOImpl();
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

        // Fetch support lists
        List<Restaurant> restaurantList = restaurantDAO.getAllRestaurants();
        List<User> userList = userDAO.getAllUsers();
        request.setAttribute("restaurantList", restaurantList);
        request.setAttribute("userList", userList);

        // Filters
        String keyword = request.getParameter("keyword");
        String filterStatus = request.getParameter("filterStatus");
        String filterPaymentStatus = request.getParameter("filterPaymentStatus");

        List<Order> orderList = orderDAO.getAllOrders();

        // Sort by ID descending (newest first)
        orderList.sort((o1, o2) -> Integer.compare(o2.getOrderId(), o1.getOrderId()));

        if (keyword != null && !keyword.trim().isEmpty()) {
            final String kw = keyword.toLowerCase().trim();
            orderList = orderList.stream()
                .filter(o -> String.valueOf(o.getOrderId()).contains(kw) || 
                             String.valueOf(o.getUserId()).contains(kw) || 
                             (o.getPaymentMethod() != null && o.getPaymentMethod().toLowerCase().contains(kw)))
                .collect(Collectors.toList());
        }

        if (filterStatus != null && !filterStatus.trim().isEmpty() && !"all".equalsIgnoreCase(filterStatus)) {
            orderList = orderList.stream()
                .filter(o -> o.getOrderStatus().equalsIgnoreCase(filterStatus))
                .collect(Collectors.toList());
        }

        if (filterPaymentStatus != null && !filterPaymentStatus.trim().isEmpty() && !"all".equalsIgnoreCase(filterPaymentStatus)) {
            orderList = orderList.stream()
                .filter(o -> o.getPaymentStatus().equalsIgnoreCase(filterPaymentStatus))
                .collect(Collectors.toList());
        }

        request.setAttribute("orderList", orderList);

        if ("view".equalsIgnoreCase(action) || "edit".equalsIgnoreCase(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Order viewOrder = orderDAO.getOrderById(id);
                if (viewOrder != null) {
                    request.setAttribute("viewOrder", viewOrder);
                    
                    // Fetch details of user and restaurant
                    User customer = userDAO.getUserById(viewOrder.getUserId());
                    Restaurant restaurant = restaurantDAO.getRestaurantById(viewOrder.getRestaurantId());
                    request.setAttribute("customer", customer);
                    request.setAttribute("restaurant", restaurant);

                    // Fetch order items & menu items
                    List<OrderItem> items = orderItemDAO.getOrderItemsByOrderId(id);
                    request.setAttribute("orderItems", items);
                    
                    List<MenuItem> menuItemsList = menuItemDAO.getAllMenuItems();
                    request.setAttribute("menuItemsList", menuItemsList);
                }
                request.setAttribute("editMode", "edit".equalsIgnoreCase(action));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("delete".equalsIgnoreCase(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                orderDAO.deleteOrder(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.sendRedirect("adminOrders");
            return;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("adminOrders.jsp");
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

        if ("updateStatus".equalsIgnoreCase(action)) {
            try {
                int orderId = Integer.parseInt(request.getParameter("orderId"));
                String orderStatus = request.getParameter("orderStatus");
                String paymentStatus = request.getParameter("paymentStatus");

                Order order = orderDAO.getOrderById(orderId);
                if (order != null) {
                    order.setOrderStatus(orderStatus);
                    order.setPaymentStatus(paymentStatus);
                    
                    if ("DELIVERED".equalsIgnoreCase(orderStatus)) {
                        order.setDeliveredAt(new java.sql.Timestamp(System.currentTimeMillis()));
                    }
                    
                    orderDAO.updateOrder(order);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect("adminOrders");
    }
}
