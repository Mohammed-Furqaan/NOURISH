<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="com.tap.model.CartItem"%>
<%@ page import="com.tap.model.MenuItem"%>
<%@ page import="com.tap.model.User"%>
<%@ page import="com.tap.DAO.MenuItemDAO"%>
<%@ page import="com.tap.DAOImpl.MenuItemDAOImpl"%>
<%
    ArrayList<CartItem> cartItems = (ArrayList<CartItem>) request.getAttribute("cartItems");
    BigDecimal grandTotal = (BigDecimal) request.getAttribute("grandTotal");
    MenuItemDAO menuDAO = new MenuItemDAOImpl();
    User loggedInUser = (User) session.getAttribute("loggedInUser");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Checkout | NOURISH</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Google Fonts & FontAwesome -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
    <!-- Style Sheets -->
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/cart.css">
</head>
<body class="bg-light">

    <!-- ================= NAVBAR ================= -->
    <header class="sticky-top">
        <nav class="navbar navbar-expand-lg navbar-light">
            <div class="container">
                <a class="navbar-brand d-flex align-items-center gap-2" href="home">
                    <i class="fa-solid fa-utensils"></i>
                    <span>NOURISH</span>
                </a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav mx-auto gap-2">
                        <li class="nav-item">
                            <a class="nav-link" href="home">Home</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="home#restaurants">Restaurants</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="myOrders">My Orders</a>
                        </li>
                    </ul>
                    <div class="d-flex align-items-center gap-2 nav-buttons">
                        <a href="cart" class="btn btn-outline-primary rounded-pill px-4 d-flex align-items-center gap-2" style="border-color: var(--primary-color); color: var(--primary-color);">
                            <i class="fa-solid fa-cart-shopping"></i> <span>Cart</span>
                        </a>
                        <% if (loggedInUser != null) { %>
                            <a href="profile" class="btn btn-outline-dark rounded-pill px-4">Profile</a>
                            <a href="logout" class="btn nav-btn">Logout</a>
                        <% } else { %>
                            <a href="login.jsp" class="btn btn-outline-dark rounded-pill px-4">Login</a>
                            <a href="register.jsp" class="btn nav-btn">Register</a>
                        <% } %>
                    </div>
                </div>
            </div>
        </nav>
    </header>

    <!-- ================= MAIN CONTAINER ================= -->
    <main class="container my-5">
        <form action="placeOrder" method="post" class="m-0">
            <div class="row g-4">
                <!-- Left: Delivery Details -->
                <div class="col-lg-8">
                    <div class="checkout-panel p-4">
                        <h2 class="fw-bold mb-4 border-bottom pb-3"><i class="fa-solid fa-map-location-dot text-primary me-2"></i>Delivery Details</h2>
                        
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label class="form-label fw-600">Full Name</label>
                                <input type="text" name="customerName" class="form-control" required placeholder="e.g. John Doe">
                            </div>
                            <div class="col-md-6 mb-3">
                                <label class="form-label fw-600">Phone Number</label>
                                <input type="text" name="phone" class="form-control" required placeholder="10-digit phone number">
                            </div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-600">Delivery Address</label>
                            <textarea name="address" rows="3" class="form-control" required placeholder="Complete home or office address details..."></textarea>
                        </div>

                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label class="form-label fw-600">City</label>
                                <input type="text" name="city" class="form-control" required placeholder="e.g. Bangalore">
                            </div>
                            <div class="col-md-6 mb-4">
                                <label class="form-label fw-600">Pincode</label>
                                <input type="text" name="pincode" class="form-control" required placeholder="6-digit pin code">
                            </div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-600">Payment Method</label>
                            <select name="paymentMethod" class="form-select">
                                <option value="COD">Cash On Delivery</option>
                                <option value="UPI">UPI</option>
                                <option value="CARD">Credit / Debit Card</option>
                                <option value="NET_BANKING">Net Banking</option>
                                <option value="WALLET">Wallet</option>
                            </select>
                        </div>
                    </div>
                </div>

                <!-- Right: Summary & Order -->
                <div class="col-lg-4">
                    <div class="checkout-panel p-4">
                        <h2 class="fw-bold mb-4 border-bottom pb-3">Order Summary</h2>
                        
                        <div class="mb-4">
                            <% if (cartItems != null) {
                                for (CartItem cartItem : cartItems) {
                                    MenuItem item = menuDAO.getMenuItemById(cartItem.getItemId());
                            %>
                            <div class="d-flex justify-content-between align-items-center mb-3 pb-3 border-bottom text-secondary small">
                                <div>
                                    <h6 class="fw-bold text-dark mb-1"><%=item.getItemName()%></h6>
                                    <span>Qty: <%=cartItem.getQuantity()%></span>
                                </div>
                                <span class="fw-bold text-dark">₹<%=cartItem.getSubtotal()%></span>
                            </div>
                            <% }
                            } %>
                        </div>
                        
                        <div class="d-flex justify-content-between mb-4">
                            <span class="fw-bold fs-5">Grand Total</span>
                            <span class="fw-bold fs-5 text-primary">₹<%=grandTotal%></span>
                        </div>
                        
                        <input type="hidden" name="grandTotal" value="<%=grandTotal%>">
                        <button type="submit" class="btn w-100 py-3 text-white fw-bold" style="background-color: var(--primary-color); border: none; border-radius: 8px;">
                            Place Order<i class="fa-solid fa-circle-check ms-2"></i>
                        </button>
                    </div>
                </div>
            </div>
        </form>
    </main>

    <!-- Bootstrap 5 Bundle JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>