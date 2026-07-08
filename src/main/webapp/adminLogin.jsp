<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Login | NOURISH</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Google Fonts & FontAwesome -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
    <!-- Style Sheets -->
    <link rel="stylesheet" href="css/style.css?v=1.5">
    <link rel="stylesheet" href="css/login.css?v=1.5">
    <style>
        .auth-banner-side {
            background: linear-gradient(180deg, rgba(0, 0, 0, 0.45) 0%, rgba(0, 0, 0, 0.15) 45%, rgba(0, 0, 0, 0.75) 100%), url("images/auth-bg.jpg") center center/cover no-repeat !important;
        }
    </style>
</head>
<body class="auth-wrapper">

    <div class="auth-container-card mx-3">
        <!-- Banner Side (Left) -->
        <div class="auth-banner-side d-none d-md-flex">
            <h1>NOURISH</h1>
            <div class="auth-banner-text">
                <h2>Admin Control <br>Console Panel</h2>
                <p>Access the backend systems to manage restaurant catalogs, view users, track order metrics, and audit settings.</p>
            </div>
        </div>

        <!-- Form Side (Right) -->
        <div class="auth-form-side">
            <div class="auth-form-logo">
                <i class="fa-solid fa-screwdriver-wrench"></i> NOURISH ADMIN
            </div>
            
            <h2 class="auth-form-title">Dashboard <span>Portal</span></h2>
            <p class="auth-form-subtitle">Sign in with administrator credentials</p>

            <%
                String error = (String) request.getAttribute("error");
                if (error != null) {
            %>
                <div class="alert alert-danger py-2 text-center small mb-3">
                    <i class="fa-solid fa-triangle-exclamation me-2"></i><%=error%>
                </div>
            <% } %>

            <form action="adminLogin" method="post" class="m-0">
                <div class="auth-input-group">
                    <i class="fa-solid fa-envelope"></i>
                    <input type="email" name="email" placeholder="Admin email address" required autocomplete="email">
                </div>

                <div class="auth-input-group">
                    <i class="fa-solid fa-lock"></i>
                    <input type="password" name="password" placeholder="Admin password" required>
                </div>

                <button type="submit" class="btn btn-auth-submit mt-4">
                    Sign In <i class="fa-solid fa-arrow-right-to-bracket ms-2"></i>
                </button>
            </form>

            <div class="text-center mt-5 pt-3 border-top">
                <a href="home" class="text-decoration-none small text-secondary fw-bold">
                    <i class="fa-solid fa-arrow-left me-2"></i>Back to Home
                </a>
            </div>
        </div>
    </div>

    <!-- Bootstrap 5 Bundle JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
