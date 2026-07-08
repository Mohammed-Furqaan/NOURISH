<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tap.model.Admin" %>
<%@ page import="com.tap.model.MenuItem" %>
<%@ page import="com.tap.model.Restaurant" %>
<%@ page import="com.tap.model.Category" %>
<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %>
<%
    Admin admin = (Admin) session.getAttribute("admin");
    if (admin == null) {
        response.sendRedirect("adminLogin.jsp");
        return;
    }

    List<MenuItem> menuItemList = (List<MenuItem>) request.getAttribute("menuItemList");
    List<Restaurant> restaurantList = (List<Restaurant>) request.getAttribute("restaurantList");
    List<Category> categoryList = (List<Category>) request.getAttribute("categoryList");
    MenuItem editMenuItem = (MenuItem) request.getAttribute("editMenuItem");
    boolean isEditMode = (editMenuItem != null);
    
    String currentRestFilter = request.getParameter("filterRestaurantId");
    String currentCatFilter = request.getParameter("filterCategoryId");
%>
<%!
    String getRestaurantName(List<Restaurant> list, int id) {
        if(list != null) {
            for(Restaurant r : list) {
                if(r.getRestaurantId() == id) return r.getRestaurantName();
            }
        }
        return "ID #" + id;
    }
    String getCategoryName(List<Category> list, int id) {
        if(list != null) {
            for(Category c : list) {
                if(c.getCategoryId() == id) return c.getCategoryName();
            }
        }
        return "ID #" + id;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>NOURISH Admin | Menu Management</title>
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
                    <a href="adminMenu" class="sidebar-link active">
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
                    <h2>Menu Item Management</h2>
                    <p>Manage menus, prices, categories, and item availabilities</p>
                </div>
                <div class="user-controls">
                    <a href="logout" class="logout-btn">
                        <i class="fa-solid fa-right-from-bracket"></i>
                        <span>Logout</span>
                    </a>
                </div>
            </header>

            <!-- Filters Bar -->
            <div class="panel-card" style="margin-bottom: 25px;">
                <div class="panel-body" style="padding: 15px 20px;">
                    <form action="adminMenu" method="get" style="display:flex; flex-wrap:wrap; gap:15px; align-items:center;">
                        <div style="flex:1; min-width:200px;">
                            <label style="font-size:12px; font-weight:600; color:var(--text-muted);">Search Keyword</label>
                            <div class="search-input-group" style="margin-top:4px;">
                                <input type="text" name="keyword" placeholder="Search by food name..." value="<%= request.getParameter("keyword") != null ? request.getParameter("keyword") : "" %>">
                            </div>
                        </div>
                        
                        <div style="min-width:180px;">
                            <label style="font-size:12px; font-weight:600; color:var(--text-muted);">Filter Restaurant</label>
                            <select name="filterRestaurantId" class="form-control" style="margin-top:4px; padding: 8px 12px; font-size:13px;" onchange="this.form.submit()">
                                <option value="all">-- All Restaurants --</option>
                                <%
                                    if(restaurantList != null) {
                                        for(Restaurant r : restaurantList) {
                                %>
                                <option value="<%= r.getRestaurantId() %>" <%= String.valueOf(r.getRestaurantId()).equals(currentRestFilter) ? "selected" : "" %>><%= r.getRestaurantName() %></option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                        </div>

                        <div style="min-width:180px;">
                            <label style="font-size:12px; font-weight:600; color:var(--text-muted);">Filter Category</label>
                            <select name="filterCategoryId" class="form-control" style="margin-top:4px; padding: 8px 12px; font-size:13px;" onchange="this.form.submit()">
                                <option value="all">-- All Categories --</option>
                                <%
                                    if(categoryList != null) {
                                        for(Category c : categoryList) {
                                %>
                                <option value="<%= c.getCategoryId() %>" <%= String.valueOf(c.getCategoryId()).equals(currentCatFilter) ? "selected" : "" %>><%= c.getCategoryName() %></option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                        </div>

                        <div style="align-self: flex-end; display:flex; gap:10px;">
                            <button type="submit" class="btn btn-primary" style="padding: 9px 18px;"><i class="fa-solid fa-filter"></i> Apply</button>
                            <a href="adminMenu" class="btn btn-secondary" style="padding: 9px 18px;">Reset</a>
                            <% if(isEditMode) { %>
                                <a href="adminMenu" class="btn btn-secondary" style="padding: 9px 18px;"><i class="fa-solid fa-plus"></i> Add New</a>
                            <% } %>
                        </div>
                    </form>
                </div>
            </div>

            <!-- CRUD Layout -->
            <div class="crud-layout">
                <!-- Left: Table Grid -->
                <div class="panel-card">
                    <div class="panel-header">
                        <div class="panel-title">
                            <h3><i class="fa-solid fa-table"></i> Menu List</h3>
                        </div>
                    </div>
                    <div class="panel-body">
                        <div class="table-responsive">
                            <table class="admin-table">
                                <thead>
                                    <tr>
                                        <th>Image</th>
                                        <th>Item Name</th>
                                        <th>Restaurant</th>
                                        <th>Category</th>
                                        <th>Price</th>
                                        <th>Type</th>
                                        <th>Status</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        if (menuItemList != null && !menuItemList.isEmpty()) {
                                            for (MenuItem m : menuItemList) {
                                                String foodImg = m.getImageUrl();
                                                if(foodImg == null || foodImg.trim().isEmpty()) {
                                                    foodImg = "images/default-food.jpg";
                                                } else if(!foodImg.startsWith("http://") && !foodImg.startsWith("https://") && !foodImg.startsWith("//")) {
                                                    foodImg = "images/" + foodImg;
                                                }
                                                String availabilityBadge = m.isAvailable() ? "badge-success" : "badge-danger";
                                                String typeBadge = "badge-success";
                                                if("NON_VEG".equalsIgnoreCase(m.getFoodType())) typeBadge = "badge-danger";
                                                else if("VEGAN".equalsIgnoreCase(m.getFoodType())) typeBadge = "badge-info";
                                    %>
                                    <tr>
                                        <td>
                                            <img src="<%= foodImg %>" alt="Food" class="img-thumbnail" onerror="this.src='https://cdn-icons-png.flaticon.com/512/1046/1046773.png'">
                                        </td>
                                        <td>
                                            <strong><%= m.getItemName() %></strong>
                                            <% if(m.getCalories() != null) { %>
                                                <div style="font-size:10px; color:var(--text-muted);"><i class="fa-solid fa-fire"></i> <%= m.getCalories() %> cal</div>
                                            <% } %>
                                        </td>
                                        <td style="font-size:12.5px;"><%= getRestaurantName(restaurantList, m.getRestaurantId()) %></td>
                                        <td style="font-size:12.5px;"><%= getCategoryName(categoryList, m.getCategoryId()) %></td>
                                        <td>
                                            <strong>₹<%= String.format("%.2f", m.getPrice()) %></strong>
                                            <% if(m.getDiscountPrice() != null && m.getDiscountPrice().compareTo(BigDecimal.ZERO) > 0) { %>
                                                <div style="font-size:11px; text-decoration:line-through; color:var(--text-muted);">₹<%= String.format("%.2f", m.getDiscountPrice()) %></div>
                                            <% } %>
                                        </td>
                                        <td>
                                            <span class="badge <%= typeBadge %>" style="font-size:10px;"><%= m.getFoodType() %></span>
                                        </td>
                                        <td>
                                            <span class="badge <%= availabilityBadge %>"><%= m.isAvailable() ? "AVAILABLE" : "OUT" %></span>
                                        </td>
                                        <td>
                                            <div style="display:flex; gap:5px;">
                                                <a href="adminMenu?action=edit&id=<%= m.getItemId() %>" class="btn btn-secondary btn-icon" title="Edit"><i class="fa-solid fa-pen-to-square"></i></a>
                                                <a href="adminMenu?action=delete&id=<%= m.getItemId() %>" class="btn btn-danger btn-icon" title="Delete" onclick="return confirm('Are you sure you want to delete this menu item?');"><i class="fa-solid fa-trash"></i></a>
                                            </div>
                                        </td>
                                    </tr>
                                    <%
                                            }
                                        } else {
                                    %>
                                    <tr>
                                        <td colspan="8" style="text-align: center; color: var(--text-muted); padding:30px;">No menu items found.</td>
                                    </tr>
                                    <%
                                        }
                                    %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <!-- Right: Form Block -->
                <div class="panel-card">
                    <div class="panel-header">
                        <div class="panel-title">
                            <h3><i class="fa-solid <%= isEditMode ? "fa-pen-to-square" : "fa-plus" %>"></i> <%= isEditMode ? "Edit Menu Item" : "Add Menu Item" %></h3>
                        </div>
                    </div>
                    <div class="panel-body">
                        <form action="adminMenu" method="post">
                            <input type="hidden" name="action" value="<%= isEditMode ? "update" : "add" %>">
                            <% if (isEditMode) { %>
                                <input type="hidden" name="itemId" value="<%= editMenuItem.getItemId() %>">
                            <% } %>
                            
                            <div class="form-group">
                                <label for="itemName">Item Name *</label>
                                <input type="text" name="itemName" id="itemName" class="form-control" value="<%= isEditMode ? editMenuItem.getItemName() : "" %>" required placeholder="e.g. Butter Paneer Masala">
                            </div>
                            
                            <div class="form-group">
                                <label for="description">Description</label>
                                <textarea name="description" id="description" class="form-control" rows="3" placeholder="Describe dish ingredients..."><%= isEditMode && editMenuItem.getDescription() != null ? editMenuItem.getDescription() : "" %></textarea>
                            </div>

                            <div class="form-row">
                                <div class="form-group">
                                    <label for="restaurantId">Restaurant *</label>
                                    <select name="restaurantId" id="restaurantId" class="form-control" required>
                                        <%
                                            if(restaurantList != null) {
                                                for(Restaurant r : restaurantList) {
                                        %>
                                        <option value="<%= r.getRestaurantId() %>" <%= isEditMode && r.getRestaurantId() == editMenuItem.getRestaurantId() ? "selected" : "" %>><%= r.getRestaurantName() %></option>
                                        <%
                                                }
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="categoryId">Category *</label>
                                    <select name="categoryId" id="categoryId" class="form-control" required>
                                        <%
                                            if(categoryList != null) {
                                                for(Category c : categoryList) {
                                        %>
                                        <option value="<%= c.getCategoryId() %>" <%= isEditMode && c.getCategoryId() == editMenuItem.getCategoryId() ? "selected" : "" %>><%= c.getCategoryName() %></option>
                                        <%
                                                }
                                            }
                                        %>
                                    </select>
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="form-group">
                                    <label for="price">Price (₹) *</label>
                                    <input type="number" step="0.01" name="price" id="price" class="form-control" value="<%= isEditMode ? editMenuItem.getPrice() : "0.00" %>" required>
                                </div>
                                <div class="form-group">
                                    <label for="discountPrice">Discount Price (₹)</label>
                                    <input type="number" step="0.01" name="discountPrice" id="discountPrice" class="form-control" value="<%= isEditMode && editMenuItem.getDiscountPrice() != null ? editMenuItem.getDiscountPrice() : "0.00" %>">
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="form-group">
                                    <label for="foodType">Food Type</label>
                                    <select name="foodType" id="foodType" class="form-control">
                                        <option value="VEG" <%= isEditMode && "VEG".equalsIgnoreCase(editMenuItem.getFoodType()) ? "selected" : "" %>>Veg</option>
                                        <option value="NON_VEG" <%= isEditMode && "NON_VEG".equalsIgnoreCase(editMenuItem.getFoodType()) ? "selected" : "" %>>Non-Veg</option>
                                        <option value="VEGAN" <%= isEditMode && "VEGAN".equalsIgnoreCase(editMenuItem.getFoodType()) ? "selected" : "" %>>Vegan</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="preparationTime">Preparation Time (mins)</label>
                                    <input type="number" name="preparationTime" id="preparationTime" class="form-control" value="<%= isEditMode ? editMenuItem.getPreparationTime() : "15" %>">
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="form-group">
                                    <label for="calories">Calories</label>
                                    <input type="number" name="calories" id="calories" class="form-control" value="<%= isEditMode && editMenuItem.getCalories() != null ? editMenuItem.getCalories() : "" %>" placeholder="e.g. 350">
                                </div>
                                <div class="form-group">
                                    <label for="quantityAvailable">Quantity Avail</label>
                                    <input type="number" name="quantityAvailable" id="quantityAvailable" class="form-control" value="<%= isEditMode ? editMenuItem.getQuantityAvailable() : "50" %>">
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="form-group">
                                    <label for="imageUrl">Image URL</label>
                                    <input type="text" name="imageUrl" id="imageUrl" class="form-control" value="<%= isEditMode && editMenuItem.getImageUrl() != null ? editMenuItem.getImageUrl() : "" %>" placeholder="dish.jpg">
                                </div>
                                <div class="form-group">
                                    <label for="rating">Rating</label>
                                    <input type="number" step="0.1" name="rating" id="rating" class="form-control" value="<%= isEditMode ? editMenuItem.getRating() : "4.0" %>" min="1" max="5">
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="form-group">
                                    <label for="totalReviews">Total Reviews</label>
                                    <input type="number" name="totalReviews" id="totalReviews" class="form-control" value="<%= isEditMode ? editMenuItem.getTotalReviews() : "0" %>">
                                </div>
                                <div class="form-group checkbox-group" style="justify-content: flex-start; padding-top: 32px;">
                                    <input type="checkbox" name="isAvailable" id="isAvailable" <%= isEditMode && editMenuItem.isAvailable() ? "checked" : (!isEditMode ? "checked" : "") %>>
                                    <label for="isAvailable">Available</label>
                                </div>
                            </div>

                            <div class="form-actions">
                                <% if (isEditMode) { %>
                                    <a href="adminMenu" class="btn btn-secondary">Cancel</a>
                                <% } %>
                                <button type="submit" class="btn btn-primary"><%= isEditMode ? "Update Item" : "Save Item" %></button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

        </main>
    </div>

</body>
</html>
