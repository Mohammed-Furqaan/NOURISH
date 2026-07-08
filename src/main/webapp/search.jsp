<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.tap.model.Restaurant"%>
<%@ page import="com.tap.model.User"%>
<%
    ArrayList<Restaurant> restaurantList = (ArrayList<Restaurant>) request.getAttribute("restaurantList");
    String keyword = (String) request.getAttribute("keyword");
    User loggedInUser = (User) session.getAttribute("loggedInUser");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Search Results | NOURISH</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Google Fonts & FontAwesome -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
    <!-- Style Sheets -->
    <link rel="stylesheet" href="css/style.css?v=1.2">
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

    <!-- ================= SEARCH RESULTS ================= -->
    <main class="container my-5" style="min-height: 60vh;">
        <div class="row">
            <div class="col-12 text-center mb-5">
                <h1 class="fw-bold">Search Results</h1>
                <p class="text-muted">Showing results for <strong>"<%=keyword%>"</strong></p>
            </div>
        </div>

        <div class="row g-4">
            <% if (restaurantList != null && !restaurantList.isEmpty()) {
                for (Restaurant restaurant : restaurantList) {
            %>
            <div class="col-md-6 col-lg-4">
                <a href="restaurantMenu?restaurantId=<%=restaurant.getRestaurantId()%>" class="restaurant-link">
                    <div class="restaurant-card">
                        <div class="restaurant-image-wrapper">
                            <%
                                String bannerImg = restaurant.getBannerImage();
                                if(bannerImg == null || bannerImg.trim().isEmpty()){
                                    bannerImg = "images/default-restaurant.jpg";
                                } else if(!bannerImg.startsWith("http://") && !bannerImg.startsWith("https://") && !bannerImg.startsWith("//")) {
                                    bannerImg = "images/" + bannerImg;
                                }
                            %>
                            <img src="<%= bannerImg %>" alt="<%= restaurant.getRestaurantName() %>">
                            <% if (restaurant.isOpen()) { %>
                                <span class="status-badge badge-open">Open</span>
                            <% } else { %>
                                <span class="status-badge badge-closed">Closed</span>
                            <% } %>
                        </div>
                        <div class="restaurant-info-body">
                            <div class="d-flex justify-content-between align-items-center mb-2">
                                <h3 class="restaurant-title mb-0"><%= restaurant.getRestaurantName() %></h3>
                                <span class="rating-badge">
                                    <%= restaurant.getRating() %> <i class="fa-solid fa-star" style="font-size: 10px;"></i>
                                </span>
                            </div>
                            <p class="restaurant-cuisine"><%= restaurant.getDescription() %></p>
                            <div class="restaurant-meta">
                                <span><%= restaurant.getAverageDeliveryTime() %> mins</span>
                                <span>₹<%= restaurant.getDeliveryFee() %> delivery</span>
                            </div>
                        </div>
                    </div>
                </a>
            </div>
            <% }
            } else { %>
            <div class="col-12 text-center py-5">
                <div class="mb-4">
                    <i class="fa-solid fa-magnifying-glass-minus text-muted" style="font-size: 80px;"></i>
                </div>
                <h3 class="text-muted mb-4">No Restaurants Found</h3>
                <a href="home" class="btn btn-primary rounded-pill text-white px-4 py-2" style="background-color: var(--primary-color); border: none;">
                    Back To Home
                </a>
            </div>
            <% } %>
        </div>
    </main>

    <!-- Bootstrap 5 Bundle JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>