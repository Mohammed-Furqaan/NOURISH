<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.tap.model.Restaurant"%>
<%@ page import="com.tap.model.Category"%>
<%@ page import="com.tap.model.User"%>
<%
    ArrayList<Restaurant> restaurantList = (ArrayList<Restaurant>) request.getAttribute("restaurantList");
    ArrayList<Category> categoryList = (ArrayList<Category>) request.getAttribute("categoryList");
    User loggedInUser = (User) session.getAttribute("loggedInUser");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>NOURISH | Food Delivery</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Google Fonts & FontAwesome -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
    <!-- Main Style Sheet -->
    <link rel="stylesheet" href="css/style.css?v=1.2">
</head>
<body>

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
                            <a class="nav-link" href="#restaurants">Restaurants</a>
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

    <!-- ================= HERO SECTION ================= -->
    <section class="hero">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-lg-8 hero-content">
                    <h1>Order Delicious Food<br>From Your Favourite Restaurants</h1>
                    <p>Fast Delivery • Fresh Food • Amazing Offers</p>
                    <form action="search" method="get">
                        <div class="search-box">
                            <input type="text" name="keyword" placeholder="Search restaurants, cuisines..." required>
                            <button type="submit">
                                <i class="fa-solid fa-magnifying-glass me-2"></i>Search
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>

    <!-- ================= POPULAR CATEGORIES ================= -->
    <section class="categories container py-5">
        <div class="section-title text-center">
            <h2>Popular Categories</h2>
            <p class="text-muted">Choose your favourite cuisine</p>
        </div>
        <div class="row g-4 justify-content-center">
            <%
            if (categoryList != null) {
                for (Category category : categoryList) {
                    String image = category.getCategoryImage();
                    if(image == null || image.trim().isEmpty()){
                        image = "images/default-category.jpg";
                    } else if(!image.startsWith("http://") && !image.startsWith("https://") && !image.startsWith("//")) {
                        image = "images/" + image;
                    }
            %>
            <div class="col-6 col-sm-4 col-md-3 col-lg-2">
                <div class="category-card">
                    <img src="<%=image%>" alt="<%=category.getCategoryName()%>">
                    <h3><%=category.getCategoryName()%></h3>
                </div>
            </div>
            <%
                }
            }
            %>
        </div>
    </section>
    
    <!-- ================= FEATURED RESTAURANTS ================= -->
    <section class="restaurants py-5" id="restaurants">
        <div class="container">
            <div class="section-title text-center">
                <h2>Featured Restaurants</h2>
                <p class="text-muted">Discover the best restaurants around you</p>
            </div>
            <div class="row g-4">
                <%
                if (restaurantList != null && !restaurantList.isEmpty()) {
                    for (Restaurant restaurant : restaurantList) {
                        String bannerImg = restaurant.getBannerImage();
                        if(bannerImg == null || bannerImg.trim().isEmpty()){
                            bannerImg = "images/default-restaurant.jpg";
                        } else if(!bannerImg.startsWith("http://") && !bannerImg.startsWith("https://") && !bannerImg.startsWith("//")) {
                            bannerImg = "images/" + bannerImg;
                        }
                %>
                <div class="col-md-6 col-lg-4">
                    <a href="restaurantMenu?restaurantId=<%=restaurant.getRestaurantId()%>" class="restaurant-link">
                        <div class="restaurant-card">
                            <div class="restaurant-image-wrapper">
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
                <%
                    }
                } else {
                %>
                <div class="col-12 text-center py-5">
                    <h3 class="text-danger">No Restaurants Available</h3>
                </div>
                <%
                }
                %>
            </div>
        </div>
    </section>

    <!-- ================= WHY CHOOSE US ================= -->
    <section class="why-us container py-5">
        <div class="section-title text-center">
            <h2>Why Choose Nourish?</h2>
            <p class="text-muted">We make food ordering simple, fast and secure.</p>
        </div>
        <div class="row g-4">
            <div class="col-md-4">
                <div class="why-us-card">
                    <i class="fa-solid fa-truck-fast"></i>
                    <h3>Fast Delivery</h3>
                    <p>Get your favourite meals delivered quickly to your doorstep.</p>
                </div>
            </div>
            <div class="col-md-4">
                <div class="why-us-card">
                    <i class="fa-solid fa-burger"></i>
                    <h3>Fresh Food</h3>
                    <p>Restaurants prepare your food only after you place your order.</p>
                </div>
            </div>
            <div class="col-md-4">
                <div class="why-us-card">
                    <i class="fa-solid fa-shield-halved"></i>
                    <h3>Safe Payments</h3>
                    <p>Multiple secure payment methods are available for checkout.</p>
                </div>
            </div>
        </div>
    </section>

    <!-- ================= FOOTER ================= -->
    <footer class="footer-section">
        <div class="container">
            <div class="row g-4">
                <div class="col-md-4">
                    <h2 class="footer-brand mb-3">NOURISH</h2>
                    <p class="text-secondary small">Delicious food delivered with love ❤️</p>
                    <div class="footer-social-icons mt-3">
                        <a href="https://www.linkedin.com/in/mohammed-furqaan/" target="_blank"><i class="fa-brands fa-linkedin-in"></i></a>
                        <a href="https://x.com/Md_Furqaan_" target="_blank"><i class="fa-brands fa-x-twitter"></i></a>
                        <a href="https://www.instagram.com/furqaannnnnnnn_/" target="_blank"><i class="fa-brands fa-instagram"></i></a>
                    </div>
                </div>
                <div class="col-md-4">
                    <h4 class="footer-heading">Quick Links</h4>
                    <ul class="footer-links list-unstyled">
                        <li><a href="home">Home</a></li>
                        <li><a href="#restaurants">Restaurants</a></li>
                        <li><a href="myOrders">My Orders</a></li>
                    </ul>
                </div>
                <div class="col-md-4">
                    <h4 class="footer-heading">Contact Us</h4>
                    <ul class="footer-links list-unstyled">
                        <li class="text-secondary small mb-2"><i class="fa-solid fa-envelope me-2"></i>mohammedfurqaan999@gmail.com</li>
                        <li class="text-secondary small mb-2"><i class="fa-solid fa-phone me-2"></i>+91 9353774230</li>
                    </ul>
                </div>
            </div>
            <div class="footer-bottom text-center">
                <p class="m-0">© 2026 Nourish. All Rights Reserved.</p>
            </div>
        </div>
    </footer>

    <!-- Bootstrap 5 Bundle JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>