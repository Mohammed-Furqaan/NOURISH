<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tap.model.Admin" %>
<%@ page import="com.tap.model.Order" %>
<%@ page import="com.tap.model.OrderItem" %>
<%@ page import="com.tap.model.MenuItem" %>
<%@ page import="com.tap.model.Restaurant" %>
<%@ page import="com.tap.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %>
<%
    Admin admin = (Admin) session.getAttribute("admin");
    if (admin == null) {
        response.sendRedirect("adminLogin.jsp");
        return;
    }

    List<Order> orderList = (List<Order>) request.getAttribute("orderList");
    List<Restaurant> restaurantList = (List<Restaurant>) request.getAttribute("restaurantList");
    List<User> userList = (List<User>) request.getAttribute("userList");
    
    Order viewOrder = (Order) request.getAttribute("viewOrder");
    User customer = (User) request.getAttribute("customer");
    Restaurant restaurant = (Restaurant) request.getAttribute("restaurant");
    List<OrderItem> orderItems = (List<OrderItem>) request.getAttribute("orderItems");
    List<MenuItem> menuItemsList = (List<MenuItem>) request.getAttribute("menuItemsList");
    
    Boolean editMode = (Boolean) request.getAttribute("editMode");
    if (editMode == null) editMode = false;
    
    boolean isInspectMode = (viewOrder != null);

    String filterStatus = request.getParameter("filterStatus");
    String filterPaymentStatus = request.getParameter("filterPaymentStatus");
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
    String getCustomerName(List<User> list, int id) {
        if(list != null) {
            for(User u : list) {
                if(u.getUserId() == id) return u.getFirstName() + " " + u.getLastName();
            }
        }
        return "User ID #" + id;
    }
    String getMenuItemName(List<MenuItem> list, int id) {
        if(list != null) {
            for(MenuItem m : list) {
                if(m.getItemId() == id) return m.getItemName();
            }
        }
        return "Item ID #" + id;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>NOURISH Admin | Order Management</title>
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
                    <a href="adminUsers" class="sidebar-link">
                        <i class="fa-solid fa-users"></i>
                        <span>Users</span>
                    </a>
                </li>
                <li class="sidebar-menu-item">
                    <a href="adminOrders" class="sidebar-link active">
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
                    <h2>Order Operations</h2>
                    <p>Track, view order details, update shipment states, and manage payments</p>
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
                    <form action="adminOrders" method="get" style="display:flex; flex-wrap:wrap; gap:15px; align-items:center;">
                        <div style="flex:1; min-width:200px;">
                            <label style="font-size:12px; font-weight:600; color:var(--text-muted);">Search Keyword</label>
                            <div class="search-input-group" style="margin-top:4px;">
                                <input type="text" name="keyword" placeholder="Search Order ID, Customer ID..." value="<%= request.getParameter("keyword") != null ? request.getParameter("keyword") : "" %>">
                            </div>
                        </div>
                        
                        <div style="min-width:180px;">
                            <label style="font-size:12px; font-weight:600; color:var(--text-muted);">Order Status</label>
                            <select name="filterStatus" class="form-control" style="margin-top:4px; padding: 8px 12px; font-size:13px;" onchange="this.form.submit()">
                                <option value="all">-- All Statuses --</option>
                                <option value="PENDING" <%= "PENDING".equals(filterStatus) ? "selected" : "" %>>PENDING</option>
                                <option value="PREPARING" <%= "PREPARING".equals(filterStatus) ? "selected" : "" %>>PREPARING</option>
                                <option value="OUT_FOR_DELIVERY" <%= "OUT_FOR_DELIVERY".equals(filterStatus) ? "selected" : "" %>>OUT FOR DELIVERY</option>
                                <option value="DELIVERED" <%= "DELIVERED".equals(filterStatus) ? "selected" : "" %>>DELIVERED</option>
                                <option value="CANCELLED" <%= "CANCELLED".equals(filterStatus) ? "selected" : "" %>>CANCELLED</option>
                            </select>
                        </div>

                        <div style="min-width:180px;">
                            <label style="font-size:12px; font-weight:600; color:var(--text-muted);">Payment Status</label>
                            <select name="filterPaymentStatus" class="form-control" style="margin-top:4px; padding: 8px 12px; font-size:13px;" onchange="this.form.submit()">
                                <option value="all">-- All Statuses --</option>
                                <option value="PENDING" <%= "PENDING".equals(filterPaymentStatus) ? "selected" : "" %>>PENDING</option>
                                <option value="SUCCESS" <%= "SUCCESS".equals(filterPaymentStatus) ? "selected" : "" %>>SUCCESS</option>
                                <option value="FAILED" <%= "FAILED".equals(filterPaymentStatus) ? "selected" : "" %>>FAILED</option>
                            </select>
                        </div>

                        <div style="align-self: flex-end; display:flex; gap:10px;">
                            <button type="submit" class="btn btn-primary" style="padding: 9px 18px;"><i class="fa-solid fa-filter"></i> Apply</button>
                            <a href="adminOrders" class="btn btn-secondary" style="padding: 9px 18px;">Reset</a>
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
                            <h3><i class="fa-solid fa-table"></i> Orders List</h3>
                        </div>
                    </div>
                    <div class="panel-body">
                        <div class="table-responsive">
                            <table class="admin-table">
                                <thead>
                                    <tr>
                                        <th>Order ID</th>
                                        <th>Customer</th>
                                        <th>Restaurant</th>
                                        <th>Final Amount</th>
                                        <th>Payment</th>
                                        <th>Order Status</th>
                                        <th>Order Date</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        if (orderList != null && !orderList.isEmpty()) {
                                            for (Order o : orderList) {
                                                String badgeClass = "badge-warning";
                                                if ("DELIVERED".equalsIgnoreCase(o.getOrderStatus())) badgeClass = "badge-success";
                                                else if ("CANCELLED".equalsIgnoreCase(o.getOrderStatus())) badgeClass = "badge-danger";
                                                else if ("OUT_FOR_DELIVERY".equalsIgnoreCase(o.getOrderStatus())) badgeClass = "badge-info";

                                                String payBadgeClass = "badge-warning";
                                                if ("SUCCESS".equalsIgnoreCase(o.getPaymentStatus())) payBadgeClass = "badge-success";
                                                else if ("FAILED".equalsIgnoreCase(o.getPaymentStatus())) payBadgeClass = "badge-danger";
                                    %>
                                    <tr>
                                        <td><strong>#<%= o.getOrderId() %></strong></td>
                                        <td><%= getCustomerName(userList, o.getUserId()) %></td>
                                        <td><%= getRestaurantName(restaurantList, o.getRestaurantId()) %></td>
                                        <td><strong>₹<%= String.format("%.2f", o.getFinalAmount()) %></strong></td>
                                        <td><span class="badge <%= payBadgeClass %>"><%= o.getPaymentStatus() %></span></td>
                                        <td><span class="badge <%= badgeClass %>"><%= o.getOrderStatus() %></span></td>
                                        <td style="font-size:12px;"><%= o.getOrderDate() %></td>
                                        <td>
                                            <div style="display:flex; gap:5px;">
                                                <a href="adminOrders?action=view&id=<%= o.getOrderId() %>" class="btn btn-secondary btn-icon" title="View Items" style="background-color:#E3F2FD; color:#1565C0;"><i class="fa-solid fa-eye"></i></a>
                                                <a href="adminOrders?action=edit&id=<%= o.getOrderId() %>" class="btn btn-secondary btn-icon" title="Update Status"><i class="fa-solid fa-pen-to-square"></i></a>
                                                <a href="adminOrders?action=delete&id=<%= o.getOrderId() %>" class="btn btn-danger btn-icon" title="Delete" onclick="return confirm('Are you sure you want to delete this order?');"><i class="fa-solid fa-trash"></i></a>
                                            </div>
                                        </td>
                                    </tr>
                                    <%
                                            }
                                        } else {
                                    %>
                                    <tr>
                                        <td colspan="8" style="text-align: center; color: var(--text-muted); padding:30px;">No orders found.</td>
                                    </tr>
                                    <%
                                        }
                                    %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <!-- Right: Form Block (View Details / Edit Status) -->
                <% if (isInspectMode) { %>
                    <div class="panel-card">
                        <div class="panel-header">
                            <div class="panel-title">
                                <h3>
                                    <% if(editMode) { %>
                                        <i class="fa-solid fa-pen-to-square"></i> Update Status Order #<%= viewOrder.getOrderId() %>
                                    <% } else { %>
                                        <i class="fa-solid fa-file-invoice-dollar"></i> Order Details #<%= viewOrder.getOrderId() %>
                                    <% } %>
                                </h3>
                            </div>
                        </div>
                        <div class="panel-body">
                            <% if(editMode) { %>                                <!-- EDIT ORDER STATUS FORM -->
                                <form action="adminOrders" method="post">
                                    <input type="hidden" name="action" value="updateStatus">
                                    <input type="hidden" name="orderId" value="<%= viewOrder.getOrderId() %>">
                                    
                                    <div class="form-group">
                                        <label for="orderStatus">Order Status</label>
                                        <select name="orderStatus" id="orderStatus" class="form-control">
                                            <option value="PLACED" <%= "PLACED".equals(viewOrder.getOrderStatus()) ? "selected" : "" %>>PLACED</option>
                                            <option value="CONFIRMED" <%= "CONFIRMED".equals(viewOrder.getOrderStatus()) ? "selected" : "" %>>CONFIRMED</option>
                                            <option value="PREPARING" <%= "PREPARING".equals(viewOrder.getOrderStatus()) ? "selected" : "" %>>PREPARING</option>
                                            <option value="READY_FOR_PICKUP" <%= "READY_FOR_PICKUP".equals(viewOrder.getOrderStatus()) ? "selected" : "" %>>READY FOR PICKUP</option>
                                            <option value="OUT_FOR_DELIVERY" <%= "OUT_FOR_DELIVERY".equals(viewOrder.getOrderStatus()) ? "selected" : "" %>>OUT FOR DELIVERY</option>
                                            <option value="DELIVERED" <%= "DELIVERED".equals(viewOrder.getOrderStatus()) ? "selected" : "" %>>DELIVERED</option>
                                            <option value="CANCELLED" <%= "CANCELLED".equals(viewOrder.getOrderStatus()) ? "selected" : "" %>>CANCELLED</option>
                                        </select>
                                    </div>
 
                                    <div class="form-group">
                                        <label for="paymentStatus">Payment Status</label>
                                        <select name="paymentStatus" id="paymentStatus" class="form-control">
                                            <option value="PENDING" <%= "PENDING".equals(viewOrder.getPaymentStatus()) ? "selected" : "" %>>PENDING</option>
                                            <option value="SUCCESS" <%= "SUCCESS".equals(viewOrder.getPaymentStatus()) ? "selected" : "" %>>SUCCESS</option>
                                            <option value="FAILED" <%= "FAILED".equals(viewOrder.getPaymentStatus()) ? "selected" : "" %>>FAILED</option>
                                            <option value="REFUNDED" <%= "REFUNDED".equals(viewOrder.getPaymentStatus()) ? "selected" : "" %>>REFUNDED</option>
                                        </select>
                                    </div>v>

                                    <div class="form-actions">
                                        <a href="adminOrders" class="btn btn-secondary">Cancel</a>
                                        <button type="submit" class="btn btn-primary">Save Changes</button>
                                    </div>
                                </form>
                            <% } else { %>
                                <!-- DETAILED VIEW PANEL -->
                                <div class="detail-card">
                                    <h4 style="margin-bottom: 12px; color: var(--primary-color);">Customer Information</h4>
                                    <div class="detail-row">
                                        <span class="detail-label">Name:</span>
                                        <span class="detail-value"><%= customer != null ? customer.getFirstName() + " " + customer.getLastName() : "User ID #" + viewOrder.getUserId() %></span>
                                    </div>
                                    <div class="detail-row">
                                        <span class="detail-label">Phone:</span>
                                        <span class="detail-value"><%= customer != null && customer.getPhoneNumber() != null ? customer.getPhoneNumber() : "N/A" %></span>
                                    </div>
                                    <div class="detail-row">
                                        <span class="detail-label">Email:</span>
                                        <span class="detail-value"><%= customer != null ? customer.getEmail() : "N/A" %></span>
                                    </div>
                                </div>

                                <div class="detail-card">
                                    <h4 style="margin-bottom: 12px; color: var(--primary-color);">Store Information</h4>
                                    <div class="detail-row">
                                        <span class="detail-label">Restaurant:</span>
                                        <span class="detail-value"><%= restaurant != null ? restaurant.getRestaurantName() : "Restaurant ID #" + viewOrder.getRestaurantId() %></span>
                                    </div>
                                    <div class="detail-row">
                                        <span class="detail-label">Store Status:</span>
                                        <span class="detail-value"><%= restaurant != null ? restaurant.getStatus() : "N/A" %></span>
                                    </div>
                                </div>

                                <div class="detail-card">
                                    <h4 style="margin-bottom: 12px; color: var(--primary-color);">Order Summary</h4>
                                    <div class="detail-row">
                                        <span class="detail-label">Subtotal:</span>
                                        <span class="detail-value">₹<%= String.format("%.2f", viewOrder.getTotalAmount()) %></span>
                                    </div>
                                    <div class="detail-row">
                                        <span class="detail-label">Delivery Fee:</span>
                                        <span class="detail-value">₹<%= String.format("%.2f", viewOrder.getDeliveryFee()) %></span>
                                    </div>
                                    <div class="detail-row">
                                        <span class="detail-label">Tax:</span>
                                        <span class="detail-value">₹<%= String.format("%.2f", viewOrder.getTaxAmount()) %></span>
                                    </div>
                                    <div class="detail-row">
                                        <span class="detail-label">Discount:</span>
                                        <span class="detail-value">₹<%= String.format("%.2f", viewOrder.getDiscountAmount()) %></span>
                                    </div>
                                    <div class="detail-row" style="border-top:1px solid #ddd; font-weight:700; margin-top:5px; padding-top:10px;">
                                        <span class="detail-label">Total Amount:</span>
                                        <span class="detail-value" style="color:var(--primary-color); font-size:16px;">₹<%= String.format("%.2f", viewOrder.getFinalAmount()) %></span>
                                    </div>
                                </div>

                                <h4 style="margin: 15px 0 10px 0; font-size:14px; font-weight:600;"><i class="fa-solid fa-list-check"></i> Ordered Items</h4>
                                <div class="table-responsive" style="margin-bottom: 20px;">
                                    <table class="admin-table" style="box-shadow:none; border: 1px solid var(--border-color);">
                                        <thead>
                                            <tr>
                                                <th>Item Name</th>
                                                <th>Price</th>
                                                <th>Qty</th>
                                                <th>Total</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%
                                                if (orderItems != null && !orderItems.isEmpty()) {
                                                    for (OrderItem item : orderItems) {
                                            %>
                                            <tr>
                                                <td><%= getMenuItemName(menuItemsList, item.getItemId()) %></td>
                                                <td>₹<%= String.format("%.2f", item.getPrice()) %></td>
                                                <td><%= item.getQuantity() %></td>
                                                <td><strong>₹<%= String.format("%.2f", item.getSubtotal()) %></strong></td>
                                            </tr>
                                            <%
                                                    }
                                                } else {
                                            %>
                                            <tr>
                                                <td colspan="4" style="text-align:center; color:var(--text-muted);">No items details.</td>
                                            </tr>
                                            <%
                                                }
                                            %>
                                        </tbody>
                                    </table>
                                </div>

                                <div class="form-actions">
                                    <a href="adminOrders?action=edit&id=<%= viewOrder.getOrderId() %>" class="btn btn-primary"><i class="fa-solid fa-pen-to-square"></i> Change Status</a>
                                    <a href="adminOrders" class="btn btn-secondary">Close</a>
                                </div>
                            <% } %>
                        </div>
                    </div>
                <% } %>
            </div>

        </main>
    </div>

</body>
</html>
