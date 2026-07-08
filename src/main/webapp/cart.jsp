<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="com.tap.model.CartItem"%>
<%@ page import="com.tap.model.MenuItem"%>
<%@ page import="com.tap.model.User"%>
<%
    ArrayList<CartItem> cartItems = (ArrayList<CartItem>) request.getAttribute("cartItems");
    HashMap<Integer, MenuItem> menuItemMap = (HashMap<Integer, MenuItem>) request.getAttribute("menuItemMap");
    BigDecimal grandTotal = BigDecimal.ZERO;
    User loggedInUser = (User) session.getAttribute("loggedInUser");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Cart | NOURISH</title>
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
    <style>
        body {
            background: linear-gradient(rgba(255, 255, 255, 0.45), rgba(255, 255, 255, 0.45)), url("<%=request.getContextPath()%>/images/favourite.png") center center/cover no-repeat !important;
            background-attachment: fixed !important;
            font-family: 'Poppins', sans-serif;
        }

        /* Cart items styling */
        .cart-item-card {
            background: rgba(255, 253, 252, 0.94) !important;
            backdrop-filter: blur(8px) !important;
            border-radius: 20px !important;
            border: 1px solid rgba(226, 220, 215, 0.6) !important;
            box-shadow: 0 15px 35px rgba(180, 140, 110, 0.06), 0 5px 15px rgba(0,0,0,0.02) !important;
            transition: all 0.3s ease !important;
        }

        .cart-item-card:hover {
            transform: translateY(-1px) !important;
            box-shadow: 0 20px 40px rgba(180, 140, 110, 0.1) !important;
        }

        /* Checkout summary card styling */
        .checkout-summary-card {
            background: rgba(255, 253, 252, 0.94) !important;
            backdrop-filter: blur(8px) !important;
            border-radius: 20px !important;
            border: 1px solid rgba(226, 220, 215, 0.6) !important;
            box-shadow: 0 15px 35px rgba(180, 140, 110, 0.06), 0 5px 15px rgba(0,0,0,0.02) !important;
            padding: 30px 24px !important;
        }

        /* Proceed button custom style */
        .btn-proceed-checkout {
            background: linear-gradient(135deg, #FF6B35, #E04F1A) !important;
            color: white !important;
            border: none !important;
            padding: 14px 20px !important;
            font-weight: 600 !important;
            border-radius: 14px !important;
            width: 100% !important;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 10px;
            box-shadow: 0 6px 20px rgba(255, 107, 53, 0.25) !important;
            transition: all 0.3s ease !important;
            text-decoration: none !important;
        }

        .btn-proceed-checkout:hover {
            background: linear-gradient(135deg, #E04F1A, #C53E10) !important;
            box-shadow: 0 10px 25px rgba(255, 107, 53, 0.4) !important;
            transform: translateY(-1px) !important;
        }
    </style>
</head>
<body class="d-flex flex-column" style="min-height: 100vh;">

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
                        <a href="cart" class="btn btn-outline-primary rounded-pill px-4 d-flex align-items-center gap-2 active" style="background-color: var(--primary-color); border-color: var(--primary-color); color: white;">
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
    <main class="container my-5" style="min-height: 65vh;">
        <div class="row">
            <div class="col-12 mb-4">
                <h1 class="fw-bold"><i class="fa-solid fa-cart-flatbed me-2"></i>Shopping Cart</h1>
                <p class="text-muted">Review items before placing order</p>
            </div>
        </div>

        <% if(cartItems != null && !cartItems.isEmpty()) { %>
            <div class="row g-4">
                <!-- Left: Cart Items List -->
                <div class="col-lg-8">
                    <div class="d-flex flex-column gap-3">
                        <% for(CartItem item : cartItems) {
                            grandTotal = grandTotal.add(item.getSubtotal());
                            MenuItem menuItem = (menuItemMap != null) ? menuItemMap.get(item.getItemId()) : null;
                            if (menuItem == null) {
                                try {
                                    com.tap.DAO.MenuItemDAO fallbackDAO = new com.tap.DAOImpl.MenuItemDAOImpl();
                                    menuItem = fallbackDAO.getMenuItemById(item.getItemId());
                                } catch (Exception e) {}
                            }
                            String itemName = (menuItem != null) ? menuItem.getItemName() : "Item #" + item.getItemId();
                        %>
                        <div class="cart-item-card p-3">
                            <div class="row align-items-center g-3">
                                <div class="col-sm-5">
                                    <h5 class="fw-bold mb-1"><%=itemName%></h5>
                                    <span class="text-muted small">Unit Price: ₹<%=item.getPrice()%></span>
                                </div>
                                <div class="col-sm-4">
                                    <div class="d-flex align-items-center justify-content-sm-center">
                                        <div class="input-group input-group-sm border rounded-pill overflow-hidden bg-white shadow-sm" style="width: 110px; border-color: #E2E8F0 !important;">
                                            <!-- Minus Button / Delete -->
                                            <% if (item.getQuantity() > 1) { %>
                                                <a href="cart?action=update&cartItemId=<%=item.getCartItemId()%>&quantity=<%=item.getQuantity() - 1%>" class="btn btn-light border-0 px-2 d-flex align-items-center justify-content-center text-secondary" style="width: 32px; background: #F8FAFC; text-decoration: none;">
                                                    <i class="fa-solid fa-minus" style="font-size: 10px;"></i>
                                                </a>
                                            <% } else { %>
                                                <a href="cart?action=remove&cartItemId=<%=item.getCartItemId()%>" class="btn btn-light border-0 px-2 d-flex align-items-center justify-content-center text-danger" style="width: 32px; background: #FFF1F2; text-decoration: none;" title="Remove Item">
                                                    <i class="fa-solid fa-trash-can" style="font-size: 10px;"></i>
                                                </a>
                                            <% } %>
                                            
                                            <!-- Quantity Text -->
                                            <span class="flex-grow-1 text-center fw-bold text-dark d-flex align-items-center justify-content-center" style="font-size: 14px; min-width: 36px; background: white;">
                                                <%=item.getQuantity()%>
                                            </span>
                                            
                                            <!-- Plus Button -->
                                            <a href="cart?action=update&cartItemId=<%=item.getCartItemId()%>&quantity=<%=item.getQuantity() + 1%>" class="btn btn-light border-0 px-2 d-flex align-items-center justify-content-center text-success" style="width: 32px; background: #F8FAFC; text-decoration: none;">
                                                <i class="fa-solid fa-plus" style="font-size: 10px;"></i>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-3 d-flex justify-content-between align-items-center justify-content-sm-end gap-3">
                                    <span class="fw-bold text-primary fs-5">₹<%=item.getSubtotal()%></span>
                                    <form action="cart" method="get" class="m-0">
                                        <input type="hidden" name="action" value="remove">
                                        <input type="hidden" name="cartItemId" value="<%=item.getCartItemId()%>">
                                        <button type="submit" class="btn btn-outline-danger btn-sm rounded-circle" style="width: 32px; height: 32px; padding: 0;" title="Remove Item">
                                            <i class="fa-solid fa-xmark"></i>
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <% } %>
                    </div>
                </div>

                <!-- Right: Summary Card -->
                <div class="col-lg-4">
                    <div class="checkout-summary-card">
                        <h4 class="fw-bold border-bottom pb-3 mb-3" style="color: #1E293B;">Order Summary</h4>
                        <div class="d-flex justify-content-between mb-3 text-secondary" style="font-weight: 500;">
                            <span>Subtotal</span>
                            <span class="text-dark">₹<%=grandTotal%></span>
                        </div>
                        <div class="d-flex justify-content-between mb-4 border-top pt-3">
                            <span class="fw-bold fs-5" style="color: #0F172A;">Grand Total</span>
                            <span class="fw-bold fs-5" style="color: #FF6B35;">₹<%=grandTotal%></span>
                        </div>
                        <form action="checkout" method="get" class="m-0">
                            <button type="submit" class="btn btn-proceed-checkout">
                                Proceed To Checkout <i class="fa-solid fa-arrow-right"></i>
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        <% } else { %>
            <div class="row justify-content-center py-5">
                <div class="col-lg-6 text-center">
                    <div class="mb-4">
                        <i class="fa-solid fa-cart-arrow-down text-muted" style="font-size: 80px;"></i>
                    </div>
                    <h3 class="text-muted mb-4">Your Cart Is Empty</h3>
                    <a href="home" class="btn btn-primary rounded-pill text-white px-4 py-2" style="background-color: var(--primary-color); border: none;">
                        Shop Delicious Food
                    </a>
                </div>
            </div>
        <% } %>
    </main>

    <!-- Bootstrap 5 Bundle JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>