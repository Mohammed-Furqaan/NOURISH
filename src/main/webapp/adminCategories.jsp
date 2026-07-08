<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tap.model.Admin" %>
<%@ page import="com.tap.model.Category" %>
<%@ page import="java.util.List" %>
<%
    Admin admin = (Admin) session.getAttribute("admin");
    if (admin == null) {
        response.sendRedirect("adminLogin.jsp");
        return;
    }

    List<Category> categoryList = (List<Category>) request.getAttribute("categoryList");
    Category editCategory = (Category) request.getAttribute("editCategory");
    boolean isEditMode = (editCategory != null);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>NOURISH Admin | Categories</title>
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
                    <a href="adminCategories" class="sidebar-link active">
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
                    <h2>Category Management</h2>
                    <p>Add, update, search, or delete food categories</p>
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
                <form action="adminCategories" method="get" class="search-form">
                    <div class="search-input-group">
                        <input type="text" name="keyword" placeholder="Search by name, description..." value="<%= request.getParameter("keyword") != null ? request.getParameter("keyword") : "" %>">
                        <button type="submit"><i class="fa-solid fa-magnifying-glass"></i></button>
                    </div>
                </form>
                <% if(isEditMode) { %>
                    <a href="adminCategories" class="btn btn-secondary"><i class="fa-solid fa-plus"></i> Add New Category</a>
                <% } %>
            </div>

            <!-- CRUD Layout -->
            <div class="crud-layout">
                <!-- Left: Table Grid -->
                <div class="panel-card">
                    <div class="panel-header">
                        <div class="panel-title">
                            <h3><i class="fa-solid fa-table"></i> Category List</h3>
                        </div>
                    </div>
                    <div class="panel-body">
                        <div class="table-responsive">
                            <table class="admin-table">
                                <thead>
                                    <tr>
                                        <th>Image</th>
                                        <th>Category ID</th>
                                        <th>Category Name</th>
                                        <th>Description</th>
                                        <th>Status</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        if (categoryList != null && !categoryList.isEmpty()) {
                                            for (Category c : categoryList) {
                                                String catImg = c.getCategoryImage();
                                                if(catImg == null || catImg.trim().isEmpty()) {
                                                    catImg = "default-category.jpg";
                                                }
                                                String statusBadge = c.isActive() ? "badge-success" : "badge-danger";
                                    %>
                                    <tr>
                                        <td>
                                            <img src="images/<%= catImg %>" alt="Image" class="img-thumbnail" onerror="this.src='https://cdn-icons-png.flaticon.com/512/3233/3233832.png'">
                                        </td>
                                        <td><strong>#<%= c.getCategoryId() %></strong></td>
                                        <td><strong><%= c.getCategoryName() %></strong></td>
                                        <td style="font-size:12px; color:var(--text-muted); max-width:200px;"><%= c.getDescription() != null ? c.getDescription() : "No description" %></td>
                                        <td>
                                            <span class="badge <%= statusBadge %>"><%= c.isActive() ? "ACTIVE" : "INACTIVE" %></span>
                                        </td>
                                        <td>
                                            <div style="display:flex; gap:5px;">
                                                <a href="adminCategories?action=edit&id=<%= c.getCategoryId() %>" class="btn btn-secondary btn-icon" title="Edit"><i class="fa-solid fa-pen-to-square"></i></a>
                                                <a href="adminCategories?action=delete&id=<%= c.getCategoryId() %>" class="btn btn-danger btn-icon" title="Delete" onclick="return confirm('Are you sure you want to delete this category?');"><i class="fa-solid fa-trash"></i></a>
                                            </div>
                                        </td>
                                    </tr>
                                    <%
                                            }
                                        } else {
                                    %>
                                    <tr>
                                        <td colspan="6" style="text-align: center; color: var(--text-muted); padding:30px;">No categories found.</td>
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
                            <h3><i class="fa-solid <%= isEditMode ? "fa-pen-to-square" : "fa-plus" %>"></i> <%= isEditMode ? "Edit Category" : "Add Category" %></h3>
                        </div>
                    </div>
                    <div class="panel-body">
                        <form action="adminCategories" method="post">
                            <input type="hidden" name="action" value="<%= isEditMode ? "update" : "add" %>">
                            <% if (isEditMode) { %>
                                <input type="hidden" name="categoryId" value="<%= editCategory.getCategoryId() %>">
                            <% } %>
                            
                            <div class="form-group">
                                <label for="categoryName">Category Name *</label>
                                <input type="text" name="categoryName" id="categoryName" class="form-control" value="<%= isEditMode ? editCategory.getCategoryName() : "" %>" required placeholder="e.g. Desserts">
                            </div>
                            
                            <div class="form-group">
                                <label for="description">Description</label>
                                <textarea name="description" id="description" class="form-control" rows="4" placeholder="Enter short category description..."><%= isEditMode && editCategory.getDescription() != null ? editCategory.getDescription() : "" %></textarea>
                            </div>

                            <div class="form-group">
                                <label for="categoryImage">Category Image File Name</label>
                                <input type="text" name="categoryImage" id="categoryImage" class="form-control" value="<%= isEditMode && editCategory.getCategoryImage() != null ? editCategory.getCategoryImage() : "" %>" placeholder="category.jpg">
                            </div>

                            <div class="form-group checkbox-group" style="margin-bottom: 25px;">
                                <input type="checkbox" name="isActive" id="isActive" <%= isEditMode && editCategory.isActive() ? "checked" : (!isEditMode ? "checked" : "") %>>
                                <label for="isActive">Category Active</label>
                            </div>

                            <div class="form-actions">
                                <% if (isEditMode) { %>
                                    <a href="adminCategories" class="btn btn-secondary">Cancel</a>
                                <% } %>
                                <button type="submit" class="btn btn-primary"><%= isEditMode ? "Update Category" : "Save Category" %></button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

        </main>
    </div>

</body>
</html>
