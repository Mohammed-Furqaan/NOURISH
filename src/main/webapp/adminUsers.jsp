<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tap.model.Admin" %>
<%@ page import="com.tap.model.User" %>
<%@ page import="java.util.List" %>
<%
    Admin admin = (Admin) session.getAttribute("admin");
    if (admin == null) {
        response.sendRedirect("adminLogin.jsp");
        return;
    }

    List<User> userList = (List<User>) request.getAttribute("userList");
    User editUser = (User) request.getAttribute("editUser");
    Boolean viewMode = (Boolean) request.getAttribute("viewMode");
    if (viewMode == null) viewMode = false;
    boolean isEditMode = (editUser != null && !viewMode);
    boolean isViewMode = (editUser != null && viewMode);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>NOURISH Admin | User Management</title>
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
                    <a href="adminMenu" class="sidebar-link">
                        <i class="fa-solid fa-bowl-food"></i>
                        <span>Menu Items</span>
                    </a>
                </li>
                <li class="sidebar-menu-item">
                    <a href="adminUsers" class="sidebar-link active">
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
                    <h2>User Account Management</h2>
                    <p>View, activate, deactivate, update, or add system users</p>
                </div>
                <div class="user-controls">
                    <a href="logout" class="logout-btn">
                        <i class="fa-solid fa-right-from-bracket"></i>
                        <span>Logout</span>
                    </a>
                </div>
            </header>

            <%
                String userError = (String) session.getAttribute("userError");
                String userSuccess = (String) session.getAttribute("userSuccess");
                if (userError != null) {
            %>
                <div class="alert alert-danger" style="margin-bottom: 20px; padding: 12px; background-color: #F8D7DA; color: #721C24; border-radius: 4px; border: 1px solid #F5C6CB; font-size: 14px;">
                    <i class="fa-solid fa-triangle-exclamation"></i> <%= userError %>
                </div>
            <%
                    session.removeAttribute("userError");
                }
                if (userSuccess != null) {
            %>
                <div class="alert alert-success" style="margin-bottom: 20px; padding: 12px; background-color: #D4EDDA; color: #155724; border-radius: 4px; border: 1px solid #C3E6CB; font-size: 14px;">
                    <i class="fa-solid fa-circle-check"></i> <%= userSuccess %>
                </div>
            <%
                    session.removeAttribute("userSuccess");
                }
            %>

            <!-- Search and Action header -->
            <div class="table-actions-header">
                <form action="adminUsers" method="get" class="search-form">
                    <div class="search-input-group">
                        <input type="text" name="keyword" placeholder="Search by name, email, phone, role..." value="<%= request.getParameter("keyword") != null ? request.getParameter("keyword") : "" %>">
                        <button type="submit"><i class="fa-solid fa-magnifying-glass"></i></button>
                    </div>
                </form>
                <% if(isEditMode || isViewMode) { %>
                    <a href="adminUsers" class="btn btn-secondary"><i class="fa-solid fa-plus"></i> Add New User</a>
                <% } %>
            </div>

            <!-- CRUD Layout -->
            <div class="crud-layout">
                <!-- Left: Table Grid -->
                <div class="panel-card">
                    <div class="panel-header">
                        <div class="panel-title">
                            <h3><i class="fa-solid fa-table"></i> Users List</h3>
                        </div>
                    </div>
                    <div class="panel-body">
                        <div class="table-responsive">
                            <table class="admin-table">
                                <thead>
                                    <tr>
                                        <th>Profile</th>
                                        <th>User ID</th>
                                        <th>Name</th>
                                        <th>Email</th>
                                        <th>Role</th>
                                        <th>Verified</th>
                                        <th>Status</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        if (userList != null && !userList.isEmpty()) {
                                            for (User u : userList) {
                                                String userImg = u.getProfileImage();
                                                if(userImg == null || userImg.trim().isEmpty()) {
                                                    userImg = "default-user.png";
                                                }
                                                String statusBadge = u.getAccountStatus().equalsIgnoreCase("ACTIVE") ? "badge-success" : "badge-danger";
                                                String verifiedBadge = u.isVerified() ? "badge-success" : "badge-warning";
                                    %>
                                    <tr>
                                        <td>
                                            <img src="images/<%= userImg %>" alt="Profile" class="img-thumbnail" onerror="this.src='https://cdn-icons-png.flaticon.com/512/3135/3135715.png'">
                                        </td>
                                        <td><strong>#<%= u.getUserId() %></strong></td>
                                        <td><strong><%= u.getFirstName() %> <%= u.getLastName() %></strong></td>
                                        <td><%= u.getEmail() %></td>
                                        <td><span class="badge badge-info" style="font-size:10px;"><%= u.getRole() %></span></td>
                                        <td>
                                            <span class="badge <%= verifiedBadge %>" style="font-size:10px;"><%= u.isVerified() ? "VERIFIED" : "PENDING" %></span>
                                        </td>
                                        <td>
                                            <span class="badge <%= statusBadge %>"><%= u.getAccountStatus() %></span>
                                        </td>
                                        <td>
                                            <div style="display:flex; gap:5px;">
                                                <a href="adminUsers?action=view&id=<%= u.getUserId() %>" class="btn btn-secondary btn-icon" title="View Details" style="background-color:#E3F2FD; color:#1565C0;"><i class="fa-solid fa-eye"></i></a>
                                                <a href="adminUsers?action=edit&id=<%= u.getUserId() %>" class="btn btn-secondary btn-icon" title="Edit"><i class="fa-solid fa-pen-to-square"></i></a>
                                                <% if(u.getAccountStatus().equalsIgnoreCase("ACTIVE")) { %>
                                                    <a href="adminUsers?action=status&id=<%= u.getUserId() %>&status=BLOCKED" class="btn btn-warning btn-icon" title="Deactivate" style="padding:0; width:32px; height:32px; background-color:#FFF3E0; color:#EF6C00;"><i class="fa-solid fa-ban"></i></a>
                                                <% } else { %>
                                                    <a href="adminUsers?action=status&id=<%= u.getUserId() %>&status=ACTIVE" class="btn btn-success btn-icon" title="Activate" style="padding:0; width:32px; height:32px; background-color:#E8F5E9; color:#2E7D32;"><i class="fa-solid fa-check"></i></a>
                                                <% } %>
                                                <a href="adminUsers?action=delete&id=<%= u.getUserId() %>" class="btn btn-danger btn-icon" title="Delete" onclick="return confirm('Are you sure you want to delete this user?');"><i class="fa-solid fa-trash"></i></a>
                                            </div>
                                        </td>
                                    </tr>
                                    <%
                                            }
                                        } else {
                                    %>
                                    <tr>
                                        <td colspan="8" style="text-align: center; color: var(--text-muted); padding:30px;">No users found.</td>
                                    </tr>
                                    <%
                                        }
                                    %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <!-- Right: Form Block (Add / Edit / View) -->
                <div class="panel-card">
                    <div class="panel-header">
                        <div class="panel-title">
                            <h3>
                                <% if(isViewMode) { %>
                                    <i class="fa-solid fa-eye"></i> View User Details
                                <% } else if(isEditMode) { %>
                                    <i class="fa-solid fa-pen-to-square"></i> Edit User Account
                                <% } else { %>
                                    <i class="fa-solid fa-plus"></i> Add New User
                                <% } %>
                            </h3>
                        </div>
                    </div>
                    <div class="panel-body">
                        <% if (isViewMode) { %>
                            <!-- VIEW DETAILS CARD -->
                            <div class="detail-card">
                                <div style="text-align:center; margin-bottom: 20px;">
                                    <%
                                        String profileImgView = editUser.getProfileImage();
                                        if (profileImgView == null || profileImgView.trim().isEmpty()) {
                                            profileImgView = "default-user.png";
                                        }
                                    %>
                                    <img src="images/<%= profileImgView %>" alt="User image" style="width:100px; height:100px; border-radius:50%; object-fit:cover; border:3px solid var(--primary-color);" onerror="this.src='https://cdn-icons-png.flaticon.com/512/3135/3135715.png'">
                                    <h4 style="margin-top:10px;"><%= editUser.getFirstName() %> <%= editUser.getLastName() %></h4>
                                    <span class="badge badge-info" style="margin-top:5px;"><%= editUser.getRole() %></span>
                                </div>
                                <div class="detail-row">
                                    <span class="detail-label">User ID:</span>
                                    <span class="detail-value">#<%= editUser.getUserId() %></span>
                                </div>
                                <div class="detail-row">
                                    <span class="detail-label">Email:</span>
                                    <span class="detail-value"><%= editUser.getEmail() %></span>
                                </div>
                                <div class="detail-row">
                                    <span class="detail-label">Phone:</span>
                                    <span class="detail-value"><%= editUser.getPhoneNumber() != null ? editUser.getPhoneNumber() : "N/A" %></span>
                                </div>
                                <div class="detail-row">
                                    <span class="detail-label">Verification:</span>
                                    <span class="detail-value"><%= editUser.isVerified() ? "Verified" : "Unverified" %></span>
                                </div>
                                <div class="detail-row">
                                    <span class="detail-label">Status:</span>
                                    <span class="detail-value"><%= editUser.getAccountStatus() %></span>
                                </div>
                                <div class="detail-row">
                                    <span class="detail-label">Created At:</span>
                                    <span class="detail-value" style="font-size:11px;"><%= editUser.getCreatedAt() %></span>
                                </div>
                                <div class="detail-row">
                                    <span class="detail-label">Last Login:</span>
                                    <span class="detail-value" style="font-size:11px;"><%= editUser.getLastLogin() != null ? editUser.getLastLogin() : "Never" %></span>
                                </div>
                            </div>
                            <div class="form-actions" style="margin-top: 15px;">
                                <a href="adminUsers?action=edit&id=<%= editUser.getUserId() %>" class="btn btn-primary"><i class="fa-solid fa-pen-to-square"></i> Edit User</a>
                                <a href="adminUsers" class="btn btn-secondary">Close</a>
                            </div>
                        <% } else { %>
                            <!-- ADD / EDIT FORM -->
                            <form action="adminUsers" method="post">
                                <input type="hidden" name="action" value="<%= isEditMode ? "update" : "add" %>">
                                <% if (isEditMode) { %>
                                    <input type="hidden" name="userId" value="<%= editUser.getUserId() %>">
                                <% } %>
                                
                                <div class="form-row">
                                    <div class="form-group">
                                        <label for="firstName">First Name *</label>
                                        <input type="text" name="firstName" id="firstName" class="form-control" value="<%= isEditMode ? editUser.getFirstName() : "" %>" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="lastName">Last Name *</label>
                                        <input type="text" name="lastName" id="lastName" class="form-control" value="<%= isEditMode ? editUser.getLastName() : "" %>" required>
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label for="email">Email Address *</label>
                                    <input type="email" name="email" id="email" class="form-control" value="<%= isEditMode ? editUser.getEmail() : "" %>" required placeholder="name@domain.com">
                                </div>
 
                                <% if (!isEditMode) { %>
                                    <div class="form-group">
                                        <label for="password">Password *</label>
                                        <input type="password" name="password" id="password" class="form-control" placeholder="••••••••" required>
                                    </div>
                                <% } %>
 
                                <div class="form-row">
                                    <div class="form-group">
                                        <label for="phoneNumber">Phone Number</label>
                                        <input type="text" name="phoneNumber" id="phoneNumber" class="form-control" value="<%= isEditMode && editUser.getPhoneNumber() != null ? editUser.getPhoneNumber() : "" %>" placeholder="10-digit number">
                                    </div>
                                    <div class="form-group">
                                        <label for="role">Role *</label>
                                        <select name="role" id="role" class="form-control" <%= isEditMode ? "disabled" : "" %> required>
                                            <option value="CUSTOMER" <%= isEditMode && "CUSTOMER".equals(editUser.getRole()) ? "selected" : "" %>>CUSTOMER</option>
                                            <option value="OWNER" <%= isEditMode && "OWNER".equals(editUser.getRole()) ? "selected" : "" %>>OWNER</option>
                                        </select>
                                    </div>
                                </div>
 
                                <div class="form-row">
                                    <div class="form-group">
                                        <label for="profileImage">Profile Image Path</label>
                                        <input type="text" name="profileImage" id="profileImage" class="form-control" value="<%= isEditMode && editUser.getProfileImage() != null ? editUser.getProfileImage() : "" %>" placeholder="user.png">
                                    </div>
                                    <div class="form-group">
                                        <label for="accountStatus">Account Status</label>
                                        <select name="accountStatus" id="accountStatus" class="form-control">
                                            <option value="ACTIVE" <%= isEditMode && "ACTIVE".equals(editUser.getAccountStatus()) ? "selected" : "" %>>ACTIVE</option>
                                            <option value="BLOCKED" <%= isEditMode && "BLOCKED".equals(editUser.getAccountStatus()) ? "selected" : "" %>>BLOCKED</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group checkbox-group" style="margin-bottom: 25px;">
                                    <input type="checkbox" name="isVerified" id="isVerified" <%= isEditMode && editUser.isVerified() ? "checked" : "" %>>
                                    <label for="isVerified">Account Email Verified</label>
                                </div>

                                <div class="form-actions">
                                    <% if (isEditMode) { %>
                                        <a href="adminUsers" class="btn btn-secondary">Cancel</a>
                                    <% } %>
                                    <button type="submit" class="btn btn-primary"><%= isEditMode ? "Update Account" : "Save Account" %></button>
                                </div>
                            </form>
                        <% } %>
                    </div>
                </div>
            </div>

        </main>
    </div>

</body>
</html>
