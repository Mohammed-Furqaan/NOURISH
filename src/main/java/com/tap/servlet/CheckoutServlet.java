package com.tap.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import com.tap.DAO.CartDAO;
import com.tap.DAO.CartItemDAO;
import com.tap.DAOImpl.CartDAOImpl;
import com.tap.DAOImpl.CartItemDAOImpl;
import com.tap.model.Cart;
import com.tap.model.CartItem;
import com.tap.model.User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Temporary User ID
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

        if (cart == null) {

            response.sendRedirect("cart");

            return;
        }

        ArrayList<CartItem> cartItems =
                (ArrayList<CartItem>) cartItemDAO
                        .getCartItemsByCartId(cart.getCartId());

        BigDecimal grandTotal = BigDecimal.ZERO;

        for (CartItem item : cartItems) {

            grandTotal =
                    grandTotal.add(item.getSubtotal());

        }

        request.setAttribute("cart", cart);
        request.setAttribute("cartItems", cartItems);
        request.setAttribute("grandTotal", grandTotal);

        RequestDispatcher rd =
                request.getRequestDispatcher("checkout.jsp");

        rd.forward(request, response);
    }
}