<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tap.model.User"%>
<%
    User user = (User) request.getAttribute("user");
    User loggedInUser = (User) session.getAttribute("loggedInUser");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Profile | NOURISH</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Google Fonts & FontAwesome -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
    <!-- Style Sheets -->
    <link rel="stylesheet" href="css/style.css?v=1.5">
    <link rel="stylesheet" href="css/profile.css?v=1.4">
    <style>
        body {
            background: linear-gradient(rgba(255, 255, 255, 0.45), rgba(255, 255, 255, 0.45)), url("<%=request.getContextPath()%>/images/profile.png") center center/cover no-repeat !important;
            background-attachment: fixed !important;
            font-family: 'Poppins', sans-serif;
            overflow-y: hidden !important; /* Remove page scrollbar */
        }
        
        /* Card structure */
        .profile-container-card {
            position: relative;
            max-width: 480px;
            width: 100%;
            background: rgba(255, 253, 252, 0.96) !important; /* Soft warm cream backdrop */
            backdrop-filter: blur(10px); /* Frosted glass blending */
            border-radius: 24px; /* Modern rounded corners */
            box-shadow: 0 20px 50px rgba(180, 140, 110, 0.12), 0 5px 15px rgba(0,0,0,0.02);
            padding: 24px 30px;
            border: 1px solid rgba(255, 107, 53, 0.05);
            overflow: hidden;
            z-index: 1;
        }

        /* Dots background in upper left corner of card */
        .profile-container-card::before {
            content: '';
            position: absolute;
            top: 20px;
            left: 20px;
            width: 50px;
            height: 70px;
            background-image: radial-gradient(#FF6B35 15%, transparent 15%);
            background-size: 8px 8px;
            opacity: 0.15;
            pointer-events: none;
        }

        /* Avatar badge style */
        .profile-avatar-wrapper {
            position: relative;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            width: 75px;
            height: 75px;
            border-radius: 50%;
            background-color: #FFF2EB; /* Soft orange tinted white */
        }

        .profile-avatar-wrapper i {
            font-size: 32px;
            color: #FF6B35;
        }

        .profile-avatar-badge {
            position: absolute;
            top: 4px;
            right: 4px;
            width: 10px;
            height: 10px;
            background-color: #FF6B35;
            border: 2px solid #FFFDFC;
            border-radius: 50%;
        }

        /* Underline accent on subtitle */
        .profile-subtitle-line {
            width: 35px;
            height: 3px;
            background-color: #FF6B35;
            margin: 8px auto 0 auto;
            border-radius: 2px;
            opacity: 0.8;
        }

        /* Input field style */
        .profile-form-input {
            background-color: #FCF9F6 !important; /* Soft beige background */
            border: 1px solid #EADCD0 !important; /* Elegant border */
            border-radius: 12px !important;
            padding: 10px 16px 10px 42px !important; /* Extra left padding for absolute icon */
            font-size: 14px !important;
            transition: all 0.3s ease;
            color: #334155;
            width: 100%;
            outline: none !important;
        }

        .profile-form-input:focus {
            background-color: #FFFFFF !important;
            border-color: #FF6B35 !important;
            box-shadow: 0 0 0 3px rgba(255, 107, 53, 0.1) !important;
        }

        /* Focus icon highlight */
        .profile-input-wrapper:focus-within i {
            color: #FF6B35 !important;
        }

        /* Update Profile Button */
        .btn-profile-submit {
            background: linear-gradient(135deg, #FF6B35, #E04F1A) !important;
            color: white !important;
            border: none !important;
            padding: 12px 20px !important;
            font-size: 15px !important;
            font-weight: 600 !important;
            border-radius: 12px !important;
            width: 100% !important;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 10px;
            box-shadow: 0 4px 15px rgba(255, 107, 53, 0.2) !important;
            transition: all 0.3s ease !important;
        }

        .btn-profile-submit:hover {
            background: linear-gradient(135deg, #E04F1A, #C53E10) !important;
            box-shadow: 0 8px 20px rgba(255, 107, 53, 0.35) !important;
            transform: translateY(-1px) !important;
        }

        /* Back to Home Button */
        .btn-profile-back {
            background-color: transparent !important;
            border: 1px solid #EADCD0 !important;
            color: #475569 !important;
            padding: 8px 20px !important;
            font-size: 14px !important;
            font-weight: 600 !important;
            border-radius: 10px !important;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            gap: 8px;
            transition: all 0.3s ease !important;
            text-decoration: none !important;
        }

        .btn-profile-back:hover {
            background-color: #FCF9F6 !important;
            border-color: #FF6B35 !important;
            color: #FF6B35 !important;
        }
    </style>
</head>
<body class="d-flex flex-column" style="min-height: 100vh;">

    <!-- ================= NAVBAR ================= -->
    <header class="sticky-top w-100">
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
                            <a href="profile" class="btn btn-outline-dark rounded-pill px-4 active" style="background-color: var(--dark-color); border-color: var(--dark-color); color: white;">Profile</a>
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
    <main class="container py-3 d-flex align-items-center justify-content-center flex-grow-1" style="min-height: calc(100vh - 80px);">
        <div class="profile-container-card">
            <div class="text-center mb-3">
                <div class="profile-avatar-wrapper mb-2">
                    <i class="fa-solid fa-user"></i>
                    <div class="profile-avatar-badge"></div>
                </div>
                <h3 class="fw-bold mb-1" style="color: var(--primary-color);">My Profile</h3>
                <p class="text-muted small mb-0">Update your account details below</p>
                <div class="profile-subtitle-line"></div>
            </div>
            
            <form action="profile" method="post" class="m-0">
                <div class="row g-2 mb-2">
                    <div class="col-sm-6">
                        <label class="form-label fw-semibold small text-secondary mb-1">First Name</label>
                        <div class="position-relative profile-input-wrapper">
                            <i class="fa-solid fa-user position-absolute text-muted" style="left: 16px; top: 50%; transform: translateY(-50%); font-size: 14px; pointer-events: none; transition: color 0.3s ease;"></i>
                            <input type="text" name="firstName" value="<%=user.getFirstName()%>" class="profile-form-input" required>
                        </div>
                    </div>
                    <div class="col-sm-6">
                        <label class="form-label fw-semibold small text-secondary mb-1">Last Name</label>
                        <div class="position-relative profile-input-wrapper">
                            <i class="fa-solid fa-user position-absolute text-muted" style="left: 16px; top: 50%; transform: translateY(-50%); font-size: 14px; pointer-events: none; transition: color 0.3s ease;"></i>
                            <input type="text" name="lastName" value="<%=user.getLastName()%>" class="profile-form-input" required>
                        </div>
                    </div>
                </div>
                
                <div class="mb-2">
                    <label class="form-label fw-semibold small text-secondary mb-1">Email Address</label>
                    <div class="position-relative profile-input-wrapper">
                        <i class="fa-solid fa-envelope position-absolute text-muted" style="left: 16px; top: 50%; transform: translateY(-50%); font-size: 14px; pointer-events: none; transition: color 0.3s ease;"></i>
                        <input type="email" name="email" value="<%=user.getEmail()%>" class="profile-form-input" required>
                    </div>
                </div>
                
                <div class="mb-3">
                    <label class="form-label fw-semibold small text-secondary mb-1">Phone Number</label>
                    <div class="position-relative profile-input-wrapper">
                        <i class="fa-solid fa-phone position-absolute text-muted" style="left: 16px; top: 50%; transform: translateY(-50%); font-size: 14px; pointer-events: none; transition: color 0.3s ease;"></i>
                        <input type="text" name="phone" value="<%=user.getPhoneNumber()%>" class="profile-form-input" required>
                    </div>
                </div>
                
                <button type="submit" class="btn btn-profile-submit">
                    Update Profile <i class="fa-solid fa-arrow-right ms-1"></i>
                </button>
            </form>
            
            <div class="text-center mt-3">
                <a href="home" class="btn-profile-back">
                    <i class="fa-solid fa-arrow-left"></i> Back to Home
                </a>
            </div>
        </div>
    </main>

    <!-- Bootstrap 5 Bundle JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>