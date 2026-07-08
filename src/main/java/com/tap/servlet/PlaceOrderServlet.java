package com.tap.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.tap.DAO.CartDAO;
import com.tap.DAO.CartItemDAO;
import com.tap.DAO.OrderDAO;
import com.tap.DAO.OrderItemDAO;

import com.tap.DAOImpl.CartDAOImpl;
import com.tap.DAOImpl.CartItemDAOImpl;
import com.tap.DAOImpl.OrderDAOImpl;
import com.tap.DAOImpl.OrderItemDAOImpl;

import com.tap.model.Cart;
import com.tap.model.CartItem;
import com.tap.model.Order;
import com.tap.model.OrderItem;
import com.tap.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/placeOrder")
public class PlaceOrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
    	System.out.println("PlaceOrderServlet Executed");

    	HttpSession session = request.getSession(false);

    	User user = (User) session.getAttribute("loggedInUser");

    	if(user == null){

    	    response.sendRedirect("login.jsp");

    	    return;
    	}

    	int userId = user.getUserId();

        CartDAO cartDAO = new CartDAOImpl();
        CartItemDAO cartItemDAO = new CartItemDAOImpl();
        OrderDAO orderDAO = new OrderDAOImpl();
        OrderItemDAO orderItemDAO = new OrderItemDAOImpl();

        Cart cart = cartDAO.getCartByUserId(userId);

        if (cart == null) {

            response.sendRedirect("cart");

            return;
        }

        List<CartItem> cartItems =
                cartItemDAO.getCartItemsByCartId(cart.getCartId());

        if (cartItems == null || cartItems.isEmpty()) {

            response.sendRedirect("cart");

            return;
        }

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItem item : cartItems) {

            totalAmount = totalAmount.add(item.getSubtotal());

        }

        Order order = new Order();

        order.setUserId(userId);

        order.setRestaurantId(cart.getRestaurantId());

        order.setAddressId(1);

        order.setCouponId(null);

        order.setTotalAmount(totalAmount);

        order.setDeliveryFee(BigDecimal.ZERO);

        order.setTaxAmount(BigDecimal.ZERO);

        order.setDiscountAmount(BigDecimal.ZERO);

        order.setFinalAmount(totalAmount);

        order.setPaymentMethod(
                request.getParameter("paymentMethod"));

        order.setPaymentStatus("SUCCESS");

        order.setOrderStatus("PLACED");

        order.setEstimatedDeliveryTime(

                new Timestamp(

                        System.currentTimeMillis()

                                + (30 * 60 * 1000)

                )

        );

        order.setDeliveredAt(null);

        int orderId = orderDAO.addOrderAndReturnId(order);

        if (orderId == 0) {

            response.sendRedirect("checkout");

            return;

        }

        for (CartItem cartItem : cartItems) {

            OrderItem orderItem = new OrderItem();

            orderItem.setOrderId(orderId);

            orderItem.setItemId(cartItem.getItemId());

            orderItem.setQuantity(cartItem.getQuantity());

            orderItem.setPrice(cartItem.getPrice());

            orderItem.setSubtotal(cartItem.getSubtotal());

            orderItemDAO.addOrderItem(orderItem);

        }

        for (CartItem cartItem : cartItems) {

            cartItemDAO.deleteCartItem(

                    cartItem.getCartItemId()

            );

        }

        cartDAO.deleteCart(cart.getCartId());

        response.sendRedirect("order-success.jsp");

    }

}