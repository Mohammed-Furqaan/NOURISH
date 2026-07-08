package com.tap.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

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

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/addToCart")
public class AddToCartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int restaurantId =
                Integer.parseInt(request.getParameter("restaurantId"));

        int itemId =
                Integer.parseInt(request.getParameter("itemId"));

        // Temporary User Id
        HttpSession session = request.getSession(false);

        User user = (User) session.getAttribute("loggedInUser");

        if(user == null){

            response.sendRedirect("login.jsp");

            return;
        }

        int userId = user.getUserId();

        CartDAO cartDAO = new CartDAOImpl();
        CartItemDAO cartItemDAO = new CartItemDAOImpl();
        MenuItemDAO menuItemDAO = new MenuItemDAOImpl();

        // Fetch Menu Item
        MenuItem menuItem = menuItemDAO.getMenuItemById(itemId);

        // Fetch Cart
        Cart cart = cartDAO.getCartByUserId(userId);

        // Create Cart if not exists
        if (cart == null) {

            Cart newCart = new Cart();

            newCart.setUserId(userId);
            newCart.setRestaurantId(restaurantId);
            newCart.setTotalAmount(BigDecimal.ZERO);
            newCart.setTotalItems(0);

            cartDAO.addCart(newCart);

            cart = cartDAO.getCartByUserId(userId);
        }

        // Restrict cart to one restaurant (automatically clear and replace)
        if (cart.getRestaurantId() != restaurantId) {
            List<CartItem> oldItems =
                    cartItemDAO.getCartItemsByCartId(cart.getCartId());
            for (CartItem item : oldItems) {
                cartItemDAO.deleteCartItem(item.getCartItemId());
            }
            cart.setRestaurantId(restaurantId);
            cartDAO.updateCart(cart);
        }

        // Check whether item already exists
        CartItem cartItem =
                cartItemDAO.getCartItem(cart.getCartId(), itemId);

        if (cartItem == null) {

            CartItem item = new CartItem();

            item.setCartId(cart.getCartId());
            item.setItemId(itemId);
            item.setQuantity(1);
            item.setPrice(menuItem.getPrice());
            item.setSubtotal(menuItem.getPrice());

            cartItemDAO.addCartItem(item);

        } else {

            int quantity = cartItem.getQuantity() + 1;

            cartItem.setQuantity(quantity);

            cartItem.setSubtotal(
                    cartItem.getPrice().multiply(
                            BigDecimal.valueOf(quantity)));

            cartItemDAO.updateCartItem(cartItem);
        }

        // Update Cart Total
        List<CartItem> cartItems =
                cartItemDAO.getCartItemsByCartId(cart.getCartId());

        BigDecimal total = BigDecimal.ZERO;
        int totalItems = 0;

        for (CartItem item : cartItems) {

            total = total.add(item.getSubtotal());
            totalItems += item.getQuantity();
        }

        cart.setTotalAmount(total);
        cart.setTotalItems(totalItems);

        cartDAO.updateCart(cart);

        response.sendRedirect("cart");
    }
}