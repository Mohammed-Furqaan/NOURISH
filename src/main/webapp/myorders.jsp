<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.tap.model.Order"%>
<%@ page import="com.tap.model.OrderItem"%>
<%@ page import="com.tap.model.MenuItem"%>
<%@ page import="com.tap.model.User"%>
<%
    ArrayList<Order> orderList = (ArrayList<Order>) request.getAttribute("orderList");
    Map<Integer, List<OrderItem>> orderItemsMap = (Map<Integer, List<OrderItem>>) request.getAttribute("orderItemsMap");
    Map<Integer, MenuItem> menuItemMap = (Map<Integer, MenuItem>) request.getAttribute("menuItemMap");
    User loggedInUser = (User) session.getAttribute("loggedInUser");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Orders | NOURISH</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Google Fonts & FontAwesome -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
    <!-- Style Sheets -->
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/orders.css">
    <style>
        body {
            background: linear-gradient(rgba(255, 255, 255, 0.45), rgba(255, 255, 255, 0.45)), url("<%=request.getContextPath()%>/images/myorder.png") center center/cover no-repeat !important;
            background-attachment: fixed !important;
            font-family: 'Poppins', sans-serif;
        }

        /* Card stylings to blend with the background */
        .order-history-card {
            background: rgba(255, 253, 252, 0.95) !important;
            backdrop-filter: blur(8px) !important;
            border-radius: 20px !important;
            border: 1px solid rgba(226, 220, 215, 0.6) !important;
            box-shadow: 0 15px 35px rgba(180, 140, 110, 0.08), 0 5px 15px rgba(0,0,0,0.02) !important;
            transition: all 0.3s ease !important;
        }

        .order-history-card:hover {
            transform: translateY(-2px) !important;
            box-shadow: 0 20px 40px rgba(180, 140, 110, 0.12), 0 8px 20px rgba(0,0,0,0.03) !important;
            border-color: rgba(255, 107, 53, 0.2) !important;
        }

        /* Order status badges */
        .status-badge-delivered {
            background-color: #E8F5E9 !important;
            color: #2E7D32 !important;
            font-weight: 600 !important;
            border: 1px solid rgba(46, 125, 50, 0.1) !important;
            border-radius: 30px !important;
        }

        .status-badge-preparing {
            background-color: #FFF3E0 !important;
            color: #EF6C00 !important;
            font-weight: 600 !important;
            border: 1px solid rgba(239, 108, 0, 0.1) !important;
            border-radius: 30px !important;
        }

        .status-badge-cancelled {
            background-color: #FFEBEE !important;
            color: #C62828 !important;
            font-weight: 600 !important;
            border: 1px solid rgba(198, 40, 40, 0.1) !important;
            border-radius: 30px !important;
        }

        .status-badge-default {
            background-color: #F5F5F5 !important;
            color: #616161 !important;
            font-weight: 600 !important;
            border: 1px solid rgba(97, 97, 97, 0.1) !important;
            border-radius: 30px !important;
        }

        /* Items row separator */
        .order-item-row {
            padding: 10px 0 !important;
            border-bottom: 1px dashed rgba(226, 220, 215, 0.5) !important;
        }

        .order-item-row:last-child {
            border-bottom: none !important;
        }

        /* Titles and styling */
        .page-title {
            color: #2C3E50 !important;
            font-weight: 700 !important;
        }

        .text-primary-accent {
            color: #FF6B35 !important;
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
                            <a class="nav-link active" style="color: var(--primary-color) !important;" href="myOrders">My Orders</a>
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
    <main class="container my-5" style="min-height: 65vh;">
        <div class="row">
            <div class="col-12 text-center mb-5">
                <h1 class="fw-bold page-title"><i class="fa-solid fa-clock-rotate-left me-2 text-primary-accent"></i>My Orders</h1>
                <p class="text-muted fw-medium">Track status or review your meal purchase history</p>
            </div>
        </div>

        <div class="row g-4 justify-content-center">
            <% if(orderList != null && !orderList.isEmpty()) {
                for(Order order : orderList) {
                    List<OrderItem> items = orderItemsMap.get(order.getOrderId());
                    
                    // Map order statuses to badge stylings
                    String statusClass = "status-badge-default";
                    String cleanStatus = order.getOrderStatus();
                    if ("DELIVERED".equalsIgnoreCase(cleanStatus)) {
                        statusClass = "status-badge-delivered";
                    } else if ("PREPARING".equalsIgnoreCase(cleanStatus) || "PLACED".equalsIgnoreCase(cleanStatus)) {
                        statusClass = "status-badge-preparing";
                    } else if ("CANCELLED".equalsIgnoreCase(cleanStatus)) {
                        statusClass = "status-badge-cancelled";
                    }
            %>
            <div class="col-lg-8">
                <div class="order-history-card p-4">
                    <div class="d-flex justify-content-between align-items-center border-bottom pb-3 mb-3 flex-wrap gap-2">
                        <div>
                            <h4 class="fw-bold mb-1">Order</h4>
                            <span class="text-secondary small"><i class="fa-solid fa-calendar-day me-2"></i><%=order.getOrderDate()%></span>
                        </div>
                        <span class="badge px-3 py-2 fs-6 <%=statusClass%>"><%=cleanStatus%></span>
                    </div>

                    <div class="order-items-list mb-3">
                        <% if(items != null) {
                            for(OrderItem item : items) {
                                MenuItem menuItem = menuItemMap.get(item.getItemId());
                        %>
                        <div class="d-flex justify-content-between align-items-center order-item-row small text-secondary">
                            <div>
                                <strong class="text-dark"><%=menuItem.getItemName()%></strong>
                                <span class="ms-2">x<%=item.getQuantity()%></span>
                            </div>
                            <span class="fw-bold text-dark">₹<%=item.getSubtotal()%></span>
                        </div>
                        <% }
                        } %>
                    </div>

                    <div class="d-flex justify-content-between align-items-center pt-3 border-top flex-wrap gap-3">
                        <div class="small text-secondary">
                            <div><strong>Restaurant ID:</strong> <%=order.getRestaurantId()%></div>
                            <div><strong>Payment Method:</strong> <%=order.getPaymentMethod()%> (<%=order.getPaymentStatus()%>)</div>
                        </div>
                        <div class="text-end">
                            <span class="small text-secondary block">Final Amount Paid</span>
                            <h4 class="fw-bold mb-0 text-primary-accent">₹<%=order.getFinalAmount()%></h4>
                        </div>
                    </div>
                </div>
            </div>
            <% }
            } else { %>
            <div class="col-12 text-center py-5">
                <div class="mb-4">
                    <i class="fa-solid fa-receipt text-muted" style="font-size: 80px;"></i>
                </div>
                <h3 class="text-muted mb-4">No Orders Found</h3>
                <a href="home" class="btn btn-primary rounded-pill text-white px-4 py-2" style="background-color: var(--primary-color); border: none;">
                    Start Ordering
                </a>
            </div>
            <% } %>
        </div>
    </main>

    <!-- Bootstrap 5 Bundle JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>