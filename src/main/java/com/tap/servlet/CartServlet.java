package com.tap.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import com.tap.DAO.CartDAO;
import com.tap.DAO.CartItemDAO;
import com.tap.DAO.MenuItemDAO;
import com.tap.DAOImpl.CartDAOImpl;
import com.tap.DAOImpl.CartItemDAOImpl;
import com.tap.DAOImpl.MenuItemDAOImpl;
import com.tap.model.Cart;
import com.tap.model.CartItem;
import com.tap.model.MenuItem;
import com.tap.model.User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if(action == null) {
            action = "view";
        }

        HttpSession session = request.getSession(false);

        User user = (User) session.getAttribute("loggedInUser");

        if(user == null){

            response.sendRedirect("login.jsp");

            return;
        }

        int userId = user.getUserId();

        CartDAO cartDAO = new CartDAOImpl();
        CartItemDAO cartItemDAO = new CartItemDAOImpl();

        Cart cart = cartDAO.getCartByUserId(userId);

        if(cart == null) {

            request.setAttribute("cartItems",
                    new ArrayList<CartItem>());

            RequestDispatcher rd =
                    request.getRequestDispatcher("cart.jsp");

            rd.forward(request,response);

            return;
        }

        switch(action) {

        case "update":

            updateQuantity(request,response,cartItemDAO);
            break;

        case "remove":

            removeItem(request,response,cartItemDAO);
            break;

        default:

            ArrayList<CartItem> cartItems =
                    (ArrayList<CartItem>)
                    cartItemDAO.getCartItemsByCartId(cart.getCartId());

            MenuItemDAO menuItemDAO = new MenuItemDAOImpl();
            java.util.HashMap<Integer, MenuItem> menuItemMap = new java.util.HashMap<>();
            for (CartItem item : cartItems) {
                if (!menuItemMap.containsKey(item.getItemId())) {
                    MenuItem menuItem = menuItemDAO.getMenuItemById(item.getItemId());
                    menuItemMap.put(item.getItemId(), menuItem);
                }
            }
            request.setAttribute("menuItemMap", menuItemMap);
            request.setAttribute("cartItems", cartItems);

            RequestDispatcher rd =
                    request.getRequestDispatcher("cart.jsp");

            rd.forward(request,response);

        }

    }

    private void updateQuantity(HttpServletRequest request,
            HttpServletResponse response,
            CartItemDAO cartItemDAO)
            throws IOException {

        int cartItemId =
                Integer.parseInt(request.getParameter("cartItemId"));

        int quantity =
                Integer.parseInt(request.getParameter("quantity"));

        CartItem item =
                cartItemDAO.getCartItemById(cartItemId);

        if(item != null){

            item.setQuantity(quantity);

            item.setSubtotal(
                    item.getPrice().multiply(
                            new BigDecimal(quantity)));

            cartItemDAO.updateCartItem(item);
        }

        response.sendRedirect("cart");
    }

    private void removeItem(HttpServletRequest request,
            HttpServletResponse response,
            CartItemDAO cartItemDAO)
            throws IOException {

        int cartItemId =
                Integer.parseInt(request.getParameter("cartItemId"));

        cartItemDAO.deleteCartItem(cartItemId);

        response.sendRedirect("cart");
    }

}