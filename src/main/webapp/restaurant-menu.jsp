<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tap.model.Restaurant"%>
<%@ page import="com.tap.model.MenuItem"%>
<%@ page import="com.tap.model.User"%>
<%@ page import="java.util.ArrayList"%>
<%
    Restaurant restaurant = (Restaurant) request.getAttribute("restaurant");
    ArrayList<MenuItem> menuList = (ArrayList<MenuItem>) request.getAttribute("menuList");
    User loggedInUser = (User) session.getAttribute("loggedInUser");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%=restaurant.getRestaurantName()%> | NOURISH</title>
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

    <!-- ================= RESTAURANT BANNER ================= -->
    <div class="w-100 overflow-hidden" style="height: 300px;">
        <%
            String bannerPath = restaurant.getBannerImage();
            if (bannerPath == null || bannerPath.trim().isEmpty()) {
                bannerPath = "images/default-banner.jpg";
            } else if (!bannerPath.startsWith("http://") && !bannerPath.startsWith("https://") && !bannerPath.startsWith("//")) {
                bannerPath = "images/" + bannerPath;
            }
        %>
        <img src="<%=bannerPath%>" class="w-100 h-100" style="object-fit: cover;" alt="<%=restaurant.getRestaurantName()%> Banner">
    </div>

    <!-- ================= RESTAURANT DETAILS ================= -->
    <main class="container my-5">
        <div class="row">
            <div class="col-12">
                <div class="restaurant-details-card p-4 mb-4">
                    <div class="d-flex justify-content-between align-items-start flex-wrap gap-3 mb-3">
                        <div>
                            <h1 class="mb-2"><%=restaurant.getRestaurantName()%></h1>
                            <p class="text-muted mb-0"><%=restaurant.getDescription()%></p>
                        </div>
                        <div class="d-flex gap-2">
                            <% if(loggedInUser != null) { %>
                                <form action="favorites" method="post" class="d-inline">
                                    <input type="hidden" name="action" value="add">
                                    <input type="hidden" name="restaurantId" value="<%=restaurant.getRestaurantId()%>">
                                    <button type="submit" class="btn btn-outline-primary rounded-pill px-4" style="border-color: var(--primary-color); color: var(--primary-color);">
                                        <i class="fa-solid fa-heart me-2"></i>Favorite
                                    </button>
                                </form>
                            <% } else { %>
                                <a href="login.jsp" class="btn btn-outline-primary rounded-pill px-4" style="border-color: var(--primary-color); color: var(--primary-color);">
                                    <i class="fa-solid fa-heart me-2"></i>Favorite
                                </a>
                            <% } %>
                            <a href="favorites" class="btn btn-dark rounded-pill px-4">
                                <i class="fa-solid fa-list-ul me-2"></i>Favorites list
                            </a>
                        </div>
                    </div>
                    
                    <div class="d-flex flex-wrap gap-3 restaurant-badge-group">
                        <span class="d-flex align-items-center gap-1">
                            <i class="fa-solid fa-star text-warning"></i> <%=restaurant.getRating()%>
                        </span>
                        <span class="d-flex align-items-center gap-1">
                            <i class="fa-solid fa-clock text-info"></i> <%=restaurant.getAverageDeliveryTime()%> mins
                        </span>
                        <span class="d-flex align-items-center gap-1">
                            <i class="fa-solid fa-truck text-success"></i> ₹<%=restaurant.getDeliveryFee()%> Delivery fee
                        </span>
                        <span class="d-flex align-items-center gap-1">
                            <i class="fa-solid fa-circle-info"></i> Min Order: ₹<%=restaurant.getMinimumOrderAmount()%>
                        </span>
                    </div>
                </div>
            </div>
        </div>

        <!-- ================= MENU ITEMS ================= -->
        <section class="menu-section mt-5">
            <div class="menu-header mb-4">
                <h2 class="mb-1">Recommended Menu</h2>
                <p class="text-muted">Delicious dishes prepared fresh for you</p>
            </div>
            
            <div class="row g-4">
                <% if(menuList != null && !menuList.isEmpty()) {
                    for(MenuItem item : menuList) {
                        String typeBadgeClass = "badge-veg";
                        if ("NON_VEG".equalsIgnoreCase(item.getFoodType())) typeBadgeClass = "badge-non-veg";
                        else if ("VEGAN".equalsIgnoreCase(item.getFoodType())) typeBadgeClass = "badge-vegan";
                        
                        String itemImg = item.getImageUrl();
                        if(itemImg == null || itemImg.trim().isEmpty()) {
                            itemImg = "images/default-food.jpg";
                        } else if(!itemImg.startsWith("http://") && !itemImg.startsWith("https://") && !itemImg.startsWith("//")) {
                            itemImg = "images/" + itemImg;
                        }
                %>
                <div class="col-md-6 col-lg-6">
                    <div class="menu-item-card h-100">
                        <div class="row g-0 h-100">
                            <div class="col-8 d-flex flex-column justify-content-between p-4">
                                <div>
                                    <div class="d-flex align-items-center gap-2 mb-2">
                                        <span class="food-type-badge <%=typeBadgeClass%>"><%=item.getFoodType()%></span>
                                        <span class="text-warning small"><i class="fa-solid fa-star"></i> <%=item.getRating()%></span>
                                    </div>
                                    <h4 class="mb-2"><%=item.getItemName()%></h4>
                                    <p class="text-muted small mb-3"><%=item.getDescription()%></p>
                                    <div class="d-flex gap-3 text-secondary small mb-3">
                                        <span><i class="fa-solid fa-clock me-1"></i><%=item.getPreparationTime()%> mins</span>
                                        <span><i class="fa-solid fa-fire me-1"></i><%=item.getCalories()%> kcal</span>
                                    </div>
                                </div>
                                
                                <div class="d-flex justify-content-between align-items-center mt-3">
                                    <div class="price-box">
                                        <% if(item.getDiscountPrice() != null && item.getDiscountPrice().doubleValue() > 0) { %>
                                            <span class="item-old-price me-2">₹<%=item.getPrice()%></span>
                                            <span class="item-new-price">₹<%=item.getDiscountPrice()%></span>
                                        <% } else { %>
                                            <span class="item-new-price">₹<%=item.getPrice()%></span>
                                        <% } %>
                                    </div>
                                    <form action="addToCart" method="get" class="m-0">
                                        <input type="hidden" name="restaurantId" value="<%=restaurant.getRestaurantId()%>">
                                        <input type="hidden" name="itemId" value="<%=item.getItemId()%>">
                                        <button type="submit" class="btn btn-add-cart">ADD</button>
                                    </form>
                                </div>
                            </div>
                            <div class="col-4 p-3 d-flex align-items-center justify-content-center">
                                <div class="w-100 h-100 rounded overflow-hidden" style="max-height: 150px;">
                                    <img src="<%=itemImg%>" alt="<%=item.getItemName()%>" class="w-100 h-100" style="object-fit: cover;">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <% } %>
            </div>
            <% } else { %>
                <div class="col-12 text-center py-5">
                    <h3 class="text-muted">No Menu Items Found</h3>
                </div>
                <% } %>
            </div>
        </section>
    </main>

    <!-- Bootstrap 5 Bundle JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>