<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.tap.model.Restaurant"%>
<%@ page import="com.tap.model.User"%>
<%
    List<Restaurant> restaurantList = (List<Restaurant>) request.getAttribute("restaurantList");
    User loggedInUser = (User) session.getAttribute("loggedInUser");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Favorites | NOURISH</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Google Fonts & FontAwesome -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
    <!-- Style Sheets -->
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/restaurant.css">
    <style>
        body {
            background: linear-gradient(rgba(255, 255, 255, 0.45), rgba(255, 255, 255, 0.45)), url("<%=request.getContextPath()%>/images/favourite.png") center center/cover no-repeat !important;
            background-attachment: fixed !important;
            font-family: 'Poppins', sans-serif;
        }

        /* Favorites cards blending */
        .fav-card {
            background: rgba(255, 253, 252, 0.95) !important;
            backdrop-filter: blur(8px) !important;
            border-radius: 20px !important;
            border: 1px solid rgba(226, 220, 215, 0.6) !important;
            box-shadow: 0 15px 35px rgba(180, 140, 110, 0.06), 0 5px 15px rgba(0,0,0,0.02) !important;
            transition: all 0.3s ease !important;
            padding: 20px !important;
        }

        .fav-card:hover {
            transform: translateY(-2px) !important;
            box-shadow: 0 20px 40px rgba(180, 140, 110, 0.12), 0 8px 20px rgba(0,0,0,0.03) !important;
            border-color: rgba(255, 107, 53, 0.2) !important;
        }

        .fav-logo-wrapper img {
            border-radius: 12px !important;
            border: 1px solid rgba(0,0,0,0.05) !important;
            width: 75px !important;
            height: 75px !important;
            object-fit: cover !important;
        }

        .fav-title {
            color: #1E293B !important;
            font-weight: 700 !important;
            font-size: 19px !important;
            margin-bottom: 4px !important;
        }

        .fav-desc {
            font-size: 13.5px !important;
            color: #64748B !important;
            line-height: 1.4 !important;
        }

        /* Buttons custom */
        .btn-fav-visit {
            background: linear-gradient(135deg, #FF6B35, #E04F1A) !important;
            color: white !important;
            border: none !important;
            font-weight: 600 !important;
            box-shadow: 0 4px 12px rgba(255, 107, 53, 0.2) !important;
            transition: all 0.3s ease !important;
        }

        .btn-fav-visit:hover {
            background: linear-gradient(135deg, #E04F1A, #C53E10) !important;
            box-shadow: 0 6px 18px rgba(255, 107, 53, 0.35) !important;
            transform: translateY(-1px) !important;
            color: white !important;
        }

        .btn-fav-remove {
            border: 1px solid #FDA4AF !important;
            color: #E11D48 !important;
            background: transparent !important;
            font-weight: 500 !important;
            transition: all 0.3s ease !important;
        }

        .btn-fav-remove:hover {
            background: #FFF1F2 !important;
            border-color: #F43F5E !important;
            color: #BE123C !important;
        }

        .page-title {
            color: #1E293B !important;
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

    <!-- ================= MAIN CONTAINER ================= -->
    <main class="container my-5" style="min-height: 60vh;">
        <div class="row">
            <div class="col-12 text-center mb-5">
                <h1 class="fw-bold page-title">
                    <i class="fa-solid fa-heart me-2 text-primary-accent"></i>My Favorite Restaurants
                </h1>
                <p class="text-muted fw-medium">Quickly access the restaurants you love most</p>
            </div>
        </div>

        <div class="row g-4 justify-content-center">
            <% if(restaurantList != null && !restaurantList.isEmpty()) {
                for(Restaurant restaurant : restaurantList) {
            %>
            <div class="col-lg-8">
                <div class="fav-card d-flex align-items-center justify-content-between p-3 gap-3 flex-wrap flex-sm-nowrap">
                    <div class="d-flex align-items-center gap-3">
                        <div class="fav-logo-wrapper flex-shrink-0">
                            <img src="<%=restaurant.getLogo()%>" alt="<%=restaurant.getRestaurantName()%> Logo" onerror="this.src='https://cdn-icons-png.flaticon.com/512/857/857681.png'">
                        </div>
                        <div>
                            <h3 class="fav-title"><%=restaurant.getRestaurantName()%></h3>
                            <p class="fav-desc mb-2 text-secondary"><%=restaurant.getDescription()%></p>
                            <div class="d-flex gap-3 text-secondary small flex-wrap">
                                <span><i class="fa-solid fa-star text-warning me-1"></i><%=restaurant.getRating()%></span>
                                <span><i class="fa-solid fa-clock text-info me-1"></i><%=restaurant.getAverageDeliveryTime()%> mins</span>
                                <span><i class="fa-solid fa-truck text-success me-1"></i>₹<%=restaurant.getDeliveryFee()%> fee</span>
                                <span>
                                    <% if(restaurant.isOpen()) { %>
                                        <span class="badge bg-success">Open</span>
                                    <% } else { %>
                                        <span class="badge bg-danger">Closed</span>
                                    <% } %>
                                </span>
                            </div>
                        </div>
                    </div>
                    
                    <div class="d-flex flex-sm-column gap-2 w-100 w-sm-auto justify-content-end">
                        <a class="btn btn-fav-visit rounded-pill px-4 py-2 w-100" href="restaurantMenu?restaurantId=<%=restaurant.getRestaurantId()%>">
                            <i class="fa-solid fa-utensils me-2"></i>Visit
                        </a>
                        <a class="btn btn-fav-remove rounded-pill px-4 py-2 w-100" href="favorites?action=remove&restaurantId=<%=restaurant.getRestaurantId()%>">
                            <i class="fa-solid fa-trash-can me-2"></i>Remove
                        </a>
                    </div>
                </div>
            </div>
            <% }
            } else { %>
            <div class="col-12 text-center py-5">
                <div class="mb-4">
                    <i class="fa-solid fa-heart-broken text-muted" style="font-size: 80px;"></i>
                </div>
                <h3 class="text-muted mb-4">No Favorite Restaurants Yet ❤️</h3>
                <a href="home" class="btn btn-primary rounded-pill text-white px-4 py-2" style="background-color: var(--primary-color); border: none;">
                    Explore Restaurants
                </a>
            </div>
            <% } %>
        </div>
    </main>

    <!-- Bootstrap 5 Bundle JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>