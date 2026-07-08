<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tap.model.Admin" %>
<%@ page import="com.tap.model.Order" %>
<%@ page import="java.util.List" %>
<%
    Admin admin = (Admin) session.getAttribute("admin");
    if (admin == null) {
        response.sendRedirect("adminLogin.jsp");
        return;
    }

    int totalUsers = (Integer) request.getAttribute("totalUsers");
    int totalRestaurants = (Integer) request.getAttribute("totalRestaurants");
    int totalCategories = (Integer) request.getAttribute("totalCategories");
    int totalMenuItems = (Integer) request.getAttribute("totalMenuItems");
    int totalOrders = (Integer) request.getAttribute("totalOrders");
    double totalRevenue = (Double) request.getAttribute("totalRevenue");
    List<Order> latestOrders = (List<Order>) request.getAttribute("latestOrders");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>NOURISH Admin | Dashboard</title>
    <!-- Fonts & Icons -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
    <!-- Custom Style Sheet -->
    <link rel="stylesheet" href="css/admin.css">
</head>
<body>

    <div class="admin-container">
        <!-- ================= SIDEBAR ================= -->
        <aside class="sidebar">
            <a href="adminDashboard" class="sidebar-brand">
                <i class="fa-solid fa-utensils"></i>
                <span>NOURISH</span>
            </a>
            
            <ul class="sidebar-menu">
                <li class="sidebar-menu-item">
                    <a href="adminDashboard" class="sidebar-link active">
                        <i class="fa-solid fa-chart-line"></i>
                        <span>Dashboard</span>
                    </a>
                </li>
                <li class="sidebar-menu-item">
                    <a href="adminRestaurants" class="sidebar-link">
                        <i class="fa-solid fa-store"></i>
                        <span>Restaurants</span>
                    </a>
                </li>
                <li class="sidebar-menu-item">
                    <a href="adminCategories" class="sidebar-link">
                        <i class="fa-solid fa-list"></i>
                        <span>Categories</span>
                    </a>
                </li>
                <li class="sidebar-menu-item">
                    <a href="adminMenu" class="sidebar-link">
                        <i class="fa-solid fa-bowl-food"></i>
                        <span>Menu Items</span>
                    </a>
                </li>
                <li class="sidebar-menu-item">
                    <a href="adminUsers" class="sidebar-link">
                        <i class="fa-solid fa-users"></i>
                        <span>Users</span>
                    </a>
                </li>
                <li class="sidebar-menu-item">
                    <a href="adminOrders" class="sidebar-link">
                        <i class="fa-solid fa-cart-shopping"></i>
                        <span>Orders</span>
                    </a>
                </li>
            </ul>
            
            <div class="sidebar-footer">
                <div class="admin-profile-summary">
                    <%
                        String profileImg = admin.getProfileImage();
                        if (profileImg == null || profileImg.trim().isEmpty()) {
                            profileImg = "default-admin.png";
                        }
                    %>
                    <img src="images/<%= profileImg %>" alt="Admin Image" onerror="this.src='https://cdn-icons-png.flaticon.com/512/3135/3135715.png'">
                    <div class="admin-info-text">
                        <div class="admin-name" style="font-weight:600;"><%= admin.getFirstName() %></div>
                        <div class="admin-name" style="font-size:10px; color:#A2A3B7;"><%= admin.getRole() %></div>
                    </div>
                </div>
            </div>
        </aside>

        <!-- ================= MAIN CONTENT ================= -->
        <main class="main-content">
            <!-- Top Navbar -->
            <header class="top-navbar">
                <div class="page-title">
                    <h2>Dashboard</h2>
                    <p>Overview of system activity and stats</p>
                </div>
                <div class="user-controls">
                    <a href="logout" class="logout-btn">
                        <i class="fa-solid fa-right-from-bracket"></i>
                        <span>Logout</span>
                    </a>
                </div>
            </header>

            <!-- Stats Grid -->
            <section class="stats-grid">
                <div class="stat-card users">
                    <div class="stat-info">
                        <h3><%= totalUsers %></h3>
                        <p>Total Users</p>
                    </div>
                    <div class="stat-icon">
                        <i class="fa-solid fa-users"></i>
                    </div>
                </div>
                <div class="stat-card restaurants">
                    <div class="stat-info">
                        <h3><%= totalRestaurants %></h3>
                        <p>Restaurants</p>
                    </div>
                    <div class="stat-icon">
                        <i class="fa-solid fa-store"></i>
                    </div>
                </div>
                <div class="stat-card categories">
                    <div class="stat-info">
                        <h3><%= totalCategories %></h3>
                        <p>Categories</p>
                    </div>
                    <div class="stat-icon">
                        <i class="fa-solid fa-list"></i>
                    </div>
                </div>
                <div class="stat-card menu">
                    <div class="stat-info">
                        <h3><%= totalMenuItems %></h3>
                        <p>Menu Items</p>
                    </div>
                    <div class="stat-icon">
                        <i class="fa-solid fa-bowl-food"></i>
                    </div>
                </div>
                <div class="stat-card orders">
                    <div class="stat-info">
                        <h3><%= totalOrders %></h3>
                        <p>Total Orders</p>
                    </div>
                    <div class="stat-icon">
                        <i class="fa-solid fa-receipt"></i>
                    </div>
                </div>
                <div class="stat-card revenue">
                    <div class="stat-info">
                        <h3>₹<%= String.format("%.2f", totalRevenue) %></h3>
                        <p>Total Revenue</p>
                    </div>
                    <div class="stat-icon">
                        <i class="fa-solid fa-indian-rupee-sign"></i>
                    </div>
                </div>
            </section>

            <!-- Dashboard Row -->
            <div class="dashboard-row">
                <!-- Latest Orders Panel -->
                <div class="panel-card">
                    <div class="panel-header">
                        <div class="panel-title">
                            <h3><i class="fa-solid fa-clock-rotate-left"></i> Latest Orders</h3>
                        </div>
                        <a href="adminOrders" class="btn btn-secondary" style="padding: 6px 12px; font-size:12px;">View All</a>
                    </div>
                    <div class="panel-body">
                        <div class="table-responsive">
                            <table class="admin-table">
                                <thead>
                                    <tr>
                                        <th>Order ID</th>
                                        <th>User ID</th>
                                        <th>Total Amount</th>
                                        <th>Payment</th>
                                        <th>Status</th>
                                        <th>Order Date</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        if (latestOrders != null && !latestOrders.isEmpty()) {
                                            for (Order order : latestOrders) {
                                                String badgeClass = "badge-warning";
                                                if ("DELIVERED".equalsIgnoreCase(order.getOrderStatus())) badgeClass = "badge-success";
                                                else if ("CANCELLED".equalsIgnoreCase(order.getOrderStatus())) badgeClass = "badge-danger";
                                                else if ("OUT_FOR_DELIVERY".equalsIgnoreCase(order.getOrderStatus())) badgeClass = "badge-info";

                                                String payBadgeClass = "badge-warning";
                                                if ("SUCCESS".equalsIgnoreCase(order.getPaymentStatus())) payBadgeClass = "badge-success";
                                                else if ("FAILED".equalsIgnoreCase(order.getPaymentStatus())) payBadgeClass = "badge-danger";
                                    %>
                                    <tr>
                                        <td><strong>#<%= order.getOrderId() %></strong></td>
                                        <td>User #<%= order.getUserId() %></td>
                                        <td>₹<%= String.format("%.2f", order.getFinalAmount()) %></td>
                                        <td><span class="badge <%= payBadgeClass %>"><%= order.getPaymentStatus() %></span></td>
                                        <td><span class="badge <%= badgeClass %>"><%= order.getOrderStatus() %></span></td>
                                        <td style="font-size:12px;"><%= order.getOrderDate() %></td>
                                    </tr>
                                    <%
                                            }
                                        } else {
                                    %>
                                    <tr>
                                        <td colspan="6" style="text-align: center; color: var(--text-muted);">No orders found.</td>
                                    </tr>
                                    <%
                                        }
                                    %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <!-- Quick Navigation Panel -->
                <div class="panel-card">
                    <div class="panel-header">
                        <div class="panel-title">
                            <h3><i class="fa-solid fa-compass"></i> Quick Actions</h3>
                        </div>
                    </div>
                    <div class="panel-body">
                        <div class="quick-nav-grid">
                            <a href="adminRestaurants" class="quick-nav-item">
                                <i class="fa-solid fa-store"></i>
                                <span>Restaurants</span>
                            </a>
                            <a href="adminCategories" class="quick-nav-item">
                                <i class="fa-solid fa-list"></i>
                                <span>Categories</span>
                            </a>
                            <a href="adminMenu" class="quick-nav-item">
                                <i class="fa-solid fa-bowl-food"></i>
                                <span>Menu Items</span>
                            </a>
                            <a href="adminUsers" class="quick-nav-item">
                                <i class="fa-solid fa-users"></i>
                                <span>Users</span>
                            </a>
                            <a href="adminOrders" class="quick-nav-item">
                                <i class="fa-solid fa-cart-shopping"></i>
                                <span>Orders</span>
                            </a>
                        </div>
                    </div>
                </div>
            </div>

        </main>
    </div>

</body>
</html>
