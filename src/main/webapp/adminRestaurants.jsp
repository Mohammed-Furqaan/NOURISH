<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tap.model.Admin" %>
<%@ page import="com.tap.model.Restaurant" %>
<%@ page import="java.util.List" %>
<%
    Admin admin = (Admin) session.getAttribute("admin");
    if (admin == null) {
        response.sendRedirect("adminLogin.jsp");
        return;
    }

    List<Restaurant> restaurantList = (List<Restaurant>) request.getAttribute("restaurantList");
    Restaurant editRestaurant = (Restaurant) request.getAttribute("editRestaurant");
    boolean isEditMode = (editRestaurant != null);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>NOURISH Admin | Restaurants</title>
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
                    <a href="adminDashboard" class="sidebar-link">
                        <i class="fa-solid fa-chart-line"></i>
                        <span>Dashboard</span>
                    </a>
                </li>
                <li class="sidebar-menu-item">
                    <a href="adminRestaurants" class="sidebar-link active">
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
                    <h2>Restaurant Management</h2>
                    <p>Add, update, search, or delete restaurant listings</p>
                </div>
                <div class="user-controls">
                    <a href="logout" class="logout-btn">
                        <i class="fa-solid fa-right-from-bracket"></i>
                        <span>Logout</span>
                    </a>
                </div>
            </header>

            <!-- Search and Action header -->
            <div class="table-actions-header">
                <form action="adminRestaurants" method="get" class="search-form">
                    <div class="search-input-group">
                        <input type="text" name="keyword" placeholder="Search by name, description..." value="<%= request.getParameter("keyword") != null ? request.getParameter("keyword") : "" %>">
                        <button type="submit"><i class="fa-solid fa-magnifying-glass"></i></button>
                    </div>
                </form>
                <% if(isEditMode) { %>
                    <a href="adminRestaurants" class="btn btn-secondary"><i class="fa-solid fa-plus"></i> Add New Restaurant</a>
                <% } %>
            </div>

            <!-- CRUD Layout -->
            <div class="crud-layout">
                <!-- Left: Table Grid -->
                <div class="panel-card">
                    <div class="panel-header">
                        <div class="panel-title">
                            <h3><i class="fa-solid fa-table"></i> Restaurant List</h3>
                        </div>
                    </div>
                    <div class="panel-body">
                        <div class="table-responsive">
                            <table class="admin-table">
                                <thead>
                                    <tr>
                                        <th>Logo</th>
                                        <th>Restaurant Name</th>
                                        <th>Owner ID</th>
                                        <th>Timing</th>
                                        <th>Rating</th>
                                        <th>Del. Fee</th>
                                        <th>Status</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        if (restaurantList != null && !restaurantList.isEmpty()) {
                                            for (Restaurant r : restaurantList) {
                                                String logoImg = r.getLogo();
                                                if(logoImg == null || logoImg.trim().isEmpty()) {
                                                    logoImg = "default-logo.png";
                                                }
                                                String statusBadge = "APPROVED".equalsIgnoreCase(r.getStatus()) ? "badge-success" : ("PENDING".equalsIgnoreCase(r.getStatus()) ? "badge-warning" : "badge-danger");
                                                String openBadge = r.isOpen() ? "badge-success" : "badge-warning";
                                    %>
                                    <tr>
                                        <td>
                                            <img src="images/<%= logoImg %>" alt="Logo" class="img-thumbnail" onerror="this.src='https://cdn-icons-png.flaticon.com/512/857/857681.png'">
                                        </td>
                                        <td>
                                            <strong><%= r.getRestaurantName() %></strong>
                                            <div style="font-size:11px; color:var(--text-muted);"><%= r.getEmail() %></div>
                                        </td>
                                        <td>User #<%= r.getOwnerId() %></td>
                                        <td style="font-size:12px;">
                                            <%= r.getOpeningTime().toString().substring(0, 5) %> - <%= r.getClosingTime().toString().substring(0, 5) %>
                                            <br>
                                            <span class="badge <%= openBadge %>" style="font-size:10px; padding:2px 6px; margin-top:4px;"><%= r.isOpen() ? "OPEN" : "CLOSED" %></span>
                                        </td>
                                        <td>
                                            <span style="color:#FFA800; font-weight:600;"><i class="fa-solid fa-star"></i> <%= r.getRating() %></span>
                                            <div style="font-size:10px; color:var(--text-muted);">(<%= r.getTotalReviews() %> revs)</div>
                                        </td>
                                        <td>₹<%= String.format("%.2f", r.getDeliveryFee()) %></td>
                                        <td>
                                            <span class="badge <%= statusBadge %>"><%= r.getStatus() %></span>
                                        </td>
                                        <td>
                                            <div style="display:flex; gap:5px;">
                                                <a href="adminRestaurants?action=edit&id=<%= r.getRestaurantId() %>" class="btn btn-secondary btn-icon" title="Edit"><i class="fa-solid fa-pen-to-square"></i></a>
                                                <a href="adminRestaurants?action=delete&id=<%= r.getRestaurantId() %>" class="btn btn-danger btn-icon" title="Delete" onclick="return confirm('Are you sure you want to delete this restaurant?');"><i class="fa-solid fa-trash"></i></a>
                                            </div>
                                        </td>
                                    </tr>
                                    <%
                                            }
                                        } else {
                                    %>
                                    <tr>
                                        <td colspan="8" style="text-align: center; color: var(--text-muted); padding:30px;">No restaurants found.</td>
                                    </tr>
                                    <%
                                        }
                                    %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <!-- Right: Form Block (Add / Edit) -->
                <div class="panel-card">
                    <div class="panel-header">
                        <div class="panel-title">
                            <h3><i class="fa-solid <%= isEditMode ? "fa-pen-to-square" : "fa-plus" %>"></i> <%= isEditMode ? "Edit Restaurant" : "Add Restaurant" %></h3>
                        </div>
                    </div>
                    <div class="panel-body">
                        <form action="adminRestaurants" method="post">
                            <input type="hidden" name="action" value="<%= isEditMode ? "update" : "add" %>">
                            <% if (isEditMode) { %>
                                <input type="hidden" name="restaurantId" value="<%= editRestaurant.getRestaurantId() %>">
                            <% } %>
                            
                            <div class="form-group">
                                <label for="restaurantName">Restaurant Name *</label>
                                <input type="text" name="restaurantName" id="restaurantName" class="form-control" value="<%= isEditMode ? editRestaurant.getRestaurantName() : "" %>" required placeholder="e.g. Pizza Palace">
                            </div>
                            
                            <div class="form-group">
                                <label for="description">Description</label>
                                <textarea name="description" id="description" class="form-control" rows="3" placeholder="Enter short menu description..."><%= isEditMode ? editRestaurant.getDescription() : "" %></textarea>
                            </div>

                            <div class="form-row">
                                <div class="form-group">
                                    <label for="ownerId">Owner ID *</label>
                                    <input type="number" name="ownerId" id="ownerId" class="form-control" value="<%= isEditMode ? editRestaurant.getOwnerId() : "1" %>" required>
                                </div>
                                <div class="form-group">
                                    <label for="status">Status</label>
                                    <select name="status" id="status" class="form-control">
                                        <option value="APPROVED" <%= isEditMode && "APPROVED".equals(editRestaurant.getStatus()) ? "selected" : "" %>>APPROVED</option>
                                        <option value="PENDING" <%= isEditMode && "PENDING".equals(editRestaurant.getStatus()) ? "selected" : "" %>>PENDING</option>
                                        <option value="REJECTED" <%= isEditMode && "REJECTED".equals(editRestaurant.getStatus()) ? "selected" : "" %>>REJECTED</option>
                                        <option value="SUSPENDED" <%= isEditMode && "SUSPENDED".equals(editRestaurant.getStatus()) ? "selected" : "" %>>SUSPENDED</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="form-group">
                                    <label for="email">Email Address</label>
                                    <input type="email" name="email" id="email" class="form-control" value="<%= isEditMode && editRestaurant.getEmail() != null ? editRestaurant.getEmail() : "" %>" placeholder="restaurant@email.com">
                                </div>
                                <div class="form-group">
                                    <label for="phoneNumber">Phone Number</label>
                                    <input type="text" name="phoneNumber" id="phoneNumber" class="form-control" value="<%= isEditMode && editRestaurant.getPhoneNumber() != null ? editRestaurant.getPhoneNumber() : "" %>" placeholder="10-digit phone">
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="form-group">
                                    <label for="logo">Logo File Name</label>
                                    <input type="text" name="logo" id="logo" class="form-control" value="<%= isEditMode && editRestaurant.getLogo() != null ? editRestaurant.getLogo() : "" %>" placeholder="logo.png">
                                </div>
                                <div class="form-group">
                                    <label for="bannerImage">Banner Image File Name</label>
                                    <input type="text" name="bannerImage" id="bannerImage" class="form-control" value="<%= isEditMode && editRestaurant.getBannerImage() != null ? editRestaurant.getBannerImage() : "" %>" placeholder="banner.jpg">
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="form-group">
                                    <label for="openingTime">Opening Time</label>
                                    <%
                                        String opTime = "09:00";
                                        if (isEditMode && editRestaurant.getOpeningTime() != null) {
                                            opTime = editRestaurant.getOpeningTime().toString().substring(0, 5);
                                        }
                                    %>
                                    <input type="time" name="openingTime" id="openingTime" class="form-control" value="<%= opTime %>">
                                </div>
                                <div class="form-group">
                                    <label for="closingTime">Closing Time</label>
                                    <%
                                        String clTime = "22:00";
                                        if (isEditMode && editRestaurant.getClosingTime() != null) {
                                            clTime = editRestaurant.getClosingTime().toString().substring(0, 5);
                                        }
                                    %>
                                    <input type="time" name="closingTime" id="closingTime" class="form-control" value="<%= clTime %>">
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="form-group">
                                    <label for="averageDeliveryTime">Delivery Mins</label>
                                    <input type="number" name="averageDeliveryTime" id="averageDeliveryTime" class="form-control" value="<%= isEditMode ? editRestaurant.getAverageDeliveryTime() : "30" %>">
                                </div>
                                <div class="form-group">
                                    <label for="minimumOrderAmount">Min Order Amount (₹)</label>
                                    <input type="number" step="0.01" name="minimumOrderAmount" id="minimumOrderAmount" class="form-control" value="<%= isEditMode ? editRestaurant.getMinimumOrderAmount() : "0.00" %>">
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="form-group">
                                    <label for="deliveryFee">Delivery Fee (₹)</label>
                                    <input type="number" step="0.01" name="deliveryFee" id="deliveryFee" class="form-control" value="<%= isEditMode ? editRestaurant.getDeliveryFee() : "30.00" %>">
                                </div>
                                <div class="form-group">
                                    <label for="rating">Rating</label>
                                    <input type="number" step="0.1" name="rating" id="rating" class="form-control" value="<%= isEditMode ? editRestaurant.getRating() : "4.0" %>" min="1" max="5">
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="form-group">
                                    <label for="totalReviews">Total Reviews</label>
                                    <input type="number" name="totalReviews" id="totalReviews" class="form-control" value="<%= isEditMode ? editRestaurant.getTotalReviews() : "0" %>">
                                </div>
                                <div class="form-group checkbox-group" style="justify-content: flex-start; padding-top: 32px;">
                                    <input type="checkbox" name="isOpen" id="isOpen" <%= isEditMode && editRestaurant.isOpen() ? "checked" : (!isEditMode ? "checked" : "") %>>
                                    <label for="isOpen">Store Open</label>
                                </div>
                            </div>

                            <div class="form-actions">
                                <% if (isEditMode) { %>
                                    <a href="adminRestaurants" class="btn btn-secondary">Cancel</a>
                                <% } %>
                                <button type="submit" class="btn btn-primary"><%= isEditMode ? "Update Restaurant" : "Save Restaurant" %></button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

        </main>
    </div>

</body>
</html>
