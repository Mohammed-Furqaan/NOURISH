package com.tap.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tap.DAO.MenuItemDAO;
import com.tap.DAO.OrderDAO;
import com.tap.DAO.OrderItemDAO;
import com.tap.DAOImpl.MenuItemDAOImpl;
import com.tap.DAOImpl.OrderDAOImpl;
import com.tap.DAOImpl.OrderItemDAOImpl;
import com.tap.model.MenuItem;
import com.tap.model.Order;
import com.tap.model.OrderItem;
import com.tap.model.User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/myOrders")
public class MyOrdersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
    	
    	HttpSession session = request.getSession(false);

    	User user = (User) session.getAttribute("loggedInUser");

    	if(user == null){

    	    response.sendRedirect("login.jsp");

    	    return;
    	}

    	int userId = user.getUserId();

        OrderDAO orderDAO = new OrderDAOImpl();
        OrderItemDAO orderItemDAO = new OrderItemDAOImpl();
        MenuItemDAO menuItemDAO = new MenuItemDAOImpl();

        // Fetch all orders of the user
        List<Order> orderList = orderDAO.getOrdersByUserId(userId);

        // Store orderId -> List<OrderItem>
        Map<Integer, List<OrderItem>> orderItemsMap = new HashMap<>();

        // Store itemId -> MenuItem
        Map<Integer, MenuItem> menuItemMap = new HashMap<>();

        for (Order order : orderList) {

            List<OrderItem> orderItems =
                    orderItemDAO.getOrderItemsByOrderId(order.getOrderId());

            orderItemsMap.put(order.getOrderId(), orderItems);

            for (OrderItem item : orderItems) {

                if (!menuItemMap.containsKey(item.getItemId())) {

                    MenuItem menuItem =
                            menuItemDAO.getMenuItemById(item.getItemId());

                    menuItemMap.put(item.getItemId(), menuItem);

                }

            }

        }

        request.setAttribute(
                "orderList",
                new ArrayList<>(orderList));

        request.setAttribute(
                "orderItemsMap",
                orderItemsMap);

        request.setAttribute(
                "menuItemMap",
                menuItemMap);

        RequestDispatcher rd =
                request.getRequestDispatcher("myorders.jsp");

        rd.forward(request, response);
    }
}