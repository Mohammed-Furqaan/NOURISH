<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tap.model.User"%>
<%
    User loggedInUser = (User) session.getAttribute("loggedInUser");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Placed Successfully | NOURISH</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Google Fonts & FontAwesome -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
    <!-- Style Sheets -->
    <link rel="stylesheet" href="css/style.css?v=1.5">
    <style>
        body {
            background: linear-gradient(rgba(255, 255, 255, 0.45), rgba(255, 255, 255, 0.45)), url("<%=request.getContextPath()%>/images/order.png") center center/cover no-repeat !important;
            background-attachment: fixed !important;
            font-family: 'Poppins', sans-serif;
            overflow-y: hidden !important; /* Single screen fit */
        }
        
        .success-checkmark-card {
            position: relative;
            max-width: 460px;
            width: 100%;
            background: rgba(255, 255, 255, 0.96) !important;
            backdrop-filter: blur(8px);
            border-radius: 24px;
            box-shadow: 0 20px 50px rgba(180, 140, 110, 0.12), 0 5px 15px rgba(0,0,0,0.02);
            padding: 35px 30px;
            border: 1px solid rgba(25, 135, 84, 0.05);
            z-index: 1;
        }

        .btn-success-track {
            background-color: #2E7D32 !important;
            color: white !important;
            border: none !important;
            padding: 12px 20px !important;
            font-size: 15.5px !important;
            font-weight: 600 !important;
            border-radius: 50px !important;
            width: 100% !important;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 10px;
            box-shadow: 0 4px 15px rgba(46, 125, 50, 0.25) !important;
            transition: all 0.3s ease !important;
            text-decoration: none !important;
        }

        .btn-success-track:hover {
            background-color: #1B5E20 !important;
            box-shadow: 0 8px 20px rgba(46, 125, 50, 0.35) !important;
            transform: translateY(-1px) !important;
        }

        .btn-outline-continue {
            background-color: transparent !important;
            border: 1px solid #FF6B35 !important;
            color: #FF6B35 !important;
            padding: 12px 20px !important;
            font-size: 15.5px !important;
            font-weight: 600 !important;
            border-radius: 50px !important;
            width: 100% !important;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 10px;
            transition: all 0.3s ease !important;
            text-decoration: none !important;
        }

        .btn-outline-continue:hover {
            background-color: #FFF8F5 !important;
            color: #E04F1A !important;
            border-color: #E04F1A !important;
        }
    </style>
</head>
<body class="d-flex align-items-center justify-content-center" style="min-height: 100vh;">

    <div class="success-checkmark-card text-center mx-3">
        <!-- Dotted circle checkmark -->
        <div class="d-inline-flex align-items-center justify-content-center mb-3">
            <div class="rounded-circle d-flex align-items-center justify-content-center" style="width: 85px; height: 85px; border: 2px dashed rgba(25, 135, 84, 0.4); padding: 5px;">
                <div class="rounded-circle d-flex align-items-center justify-content-center" style="width: 100%; height: 100%; background-color: #E8F5E9 !important;">
                    <div class="bg-success rounded-circle d-flex align-items-center justify-content-center" style="width: 48px; height: 48px;">
                        <i class="fa-solid fa-check text-white" style="font-size: 22px;"></i>
                    </div>
                </div>
            </div>
        </div>
        
        <h2 class="fw-bold text-dark mb-2" style="font-size: 26px;">Order Placed <br><span class="text-success">Successfully!</span></h2>
        <p class="text-secondary small mb-4 px-2" style="font-size: 13.5px; line-height: 1.45;">
            Thank you for ordering from <strong style="color: #FF6B35;">Nourish</strong>. Your delicious food is being prepared and will arrive soon.
        </p>

        <!-- Delivery Time Alert Box -->
        <div class="d-flex align-items-center gap-3 p-3 mb-4 text-start" style="background-color: #F1F8F5; border-radius: 14px; border: 1px solid rgba(25, 135, 84, 0.08);">
            <div class="d-flex align-items-center justify-content-center rounded-circle bg-white" style="width: 44px; height: 44px; color: #2E7D32; box-shadow: 0 4px 10px rgba(0,0,0,0.02);">
                <i class="fa-solid fa-stopwatch" style="font-size: 18px;"></i>
            </div>
            <div>
                <div class="text-secondary small fw-medium" style="font-size: 11px; letter-spacing: 0.5px;">Estimated Delivery Time</div>
                <div class="fw-bold text-dark" style="font-size: 15px;">30 - 40 mins</div>
            </div>
        </div>

        <!-- Buttons -->
        <div class="d-flex flex-column gap-2.5">
            <a href="myOrders" class="btn-success-track">
                <i class="fa-solid fa-clipboard-list"></i> Track My Orders
            </a>
            <a href="home" class="btn-outline-continue mt-2">
                <i class="fa-solid fa-house"></i> Continue Shopping
            </a>
        </div>

        <!-- Delivery Scooter Illustration SVG -->
        <div class="d-flex justify-content-center mt-4">
            <svg viewBox="0 0 260 40" width="100%" height="35" style="max-width: 220px; opacity: 0.85;">
                <!-- Dashed Path -->
                <path d="M10,25 Q70,25 130,25 T250,25" fill="none" stroke="#E2E8F0" stroke-width="2" stroke-dasharray="4 4" />
                <!-- Destination Pin -->
                <g transform="translate(230, 10)">
                    <path d="M8,0 C3.5,0 0,3.5 0,8 C0,14 8,22 8,22 C8,22 16,14 16,8 C16,3.5 12.5,0 8,0 Z" fill="#FF6B35" />
                    <circle cx="8" cy="8" r="3.5" fill="white" />
                </g>
                <!-- Delivery Boy Scooter Group -->
                <g transform="translate(60, -5)">
                    <!-- Scooter Wheels -->
                    <circle cx="12" cy="32" r="5" fill="#475569" stroke="white" stroke-width="1.5" />
                    <circle cx="38" cy="32" r="5" fill="#475569" stroke="white" stroke-width="1.5" />
                    <!-- Scooter Body -->
                    <path d="M12,32 L20,32 L28,24 L38,32" stroke="#2E7D32" stroke-width="3.5" stroke-linecap="round" stroke-linejoin="round" fill="none" />
                    <path d="M28,24 L34,12" stroke="#2E7D32" stroke-width="2.5" stroke-linecap="round" fill="none" />
                    <!-- Delivery Box -->
                    <rect x="0" y="14" width="11" height="11" rx="2" fill="#FF6B35" />
                    <path d="M2,18 L9,18" stroke="white" stroke-width="1" />
                    <!-- Driver Body -->
                    <circle cx="22" cy="6" r="3.5" fill="#334155" /> <!-- Head -->
                    <path d="M22,9.5 C19,12 18,17 18,22 L24,22 Z" fill="#334155" /> <!-- Body -->
                    <path d="M24,14 L29,18" stroke="#334155" stroke-width="2" stroke-linecap="round" /> <!-- Arm -->
                </g>
            </svg>
        </div>
    </div>

    <!-- Bootstrap 5 Bundle JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>