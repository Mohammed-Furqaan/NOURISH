# 🍽️ Nourish – Food Delivery & Restaurant Management System

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk" />
  <img src="https://img.shields.io/badge/Jakarta%20EE-Servlets-blue?style=for-the-badge" />
  <img src="https://img.shields.io/badge/MySQL-Database-blue?style=for-the-badge&logo=mysql" />
  <img src="https://img.shields.io/badge/Apache%20Tomcat-10.1-red?style=for-the-badge&logo=apachetomcat" />
  <img src="https://img.shields.io/badge/Architecture-MVC-success?style=for-the-badge" />
  <img src="https://img.shields.io/badge/Design%20Pattern-DAO-success?style=for-the-badge" />
</p>

<p align="center">
A modern full-stack Food Delivery and Restaurant Management System developed using Java EE (Servlets & JSP), following the MVC Architecture and DAO Design Pattern.
</p>

---

## 📖 Overview

**Nourish** is a full-stack web application that enables customers to discover restaurants, browse menus, manage carts, place orders, and view order history through an intuitive interface.

The application also includes a powerful **Admin Dashboard** for managing restaurants, menus, categories, users, and customer orders.

The project is built using **Java Servlets**, **JSP**, **JDBC**, and **MySQL**, following industry-standard software architecture principles including **MVC** and **DAO**.

---

# ✨ Features

## 👤 Customer Module

- User Registration & Secure Login
- Restaurant Listing & Search
- Restaurant Details & Menus
- Add to Cart
- Update Cart Quantity
- Remove Cart Items
- Favorites / Wishlist
- Secure Checkout
- Mock Payment Integration
- Order Placement
- Order History
- User Profile Management

---

## 👨‍💼 Admin Module

- Dashboard Overview
- Restaurant Management (CRUD)
- Menu Management
- Category Management
- User Management
- Order Management
- Delivery Charge Management
- Restaurant Status Control

---

# 🏗️ Architecture

The project follows the **Model-View-Controller (MVC)** architecture along with the **DAO (Data Access Object)** design pattern.

```
               Client
                  │
                  ▼
          JSP (View Layer)
                  │
                  ▼
        Servlet (Controller)
                  │
                  ▼
            DAO Layer (JDBC)
                  │
                  ▼
             MySQL Database
```

---

# 🛠️ Tech Stack

| Category | Technology |
|----------|------------|
| Language | Java SE 21 |
| Backend | Jakarta Servlet API 5.0 |
| Frontend | JSP, HTML5, CSS3, JavaScript |
| Database | MySQL |
| Server | Apache Tomcat 10.1 |
| Architecture | MVC |
| Design Pattern | DAO |
| IDE | Eclipse IDE |
| Version Control | Git & GitHub |

---

# 📂 Project Structure

```
Nourish
│
├── src
│   └── main
│       ├── java
│       │   └── com.tap
│       │       ├── DAO
│       │       ├── DAOImpl
│       │       ├── model
│       │       ├── servlet
│       │       └── utility
│       │
│       └── webapp
│           ├── css
│           ├── images
│           ├── WEB-INF
│           ├── js
│           └── *.jsp
│
├── database
│   └── nourish.sql
│
└── README.md
```

---

# 🗄️ Database

Database Name

```sql
nourish_db
```

### Main Tables

- Users
- Restaurants
- Categories
- Menu Items
- Orders
- Order Items

> Import the provided SQL script before running the application.

---

# ⚙️ Installation

## 1️⃣ Clone the Repository

```bash
git clone https://github.com/Mohammed-Furqaan/NOURISH.git
```

---

## 2️⃣ Configure MySQL

Create a database named:

```sql
CREATE DATABASE nourish_db;
```

Import the SQL file into MySQL.

---

## 3️⃣ Configure Database Connection

Update the database credentials in:

```
src/main/java/com/tap/utility/DBConnection.java
```

```java
private static final String URL = "jdbc:mysql://localhost:3306/nourish_db";
private static final String USERNAME = "root";
private static final String PASSWORD = "your_password";
```

---

## 4️⃣ Configure Apache Tomcat

- Import the project into Eclipse.
- Configure Apache Tomcat 10.1.
- Add the project to the server.
- Run the application.

---

## 5️⃣ Access the Application

```
http://localhost:8080/Nourish/
```

---

# 🚀 Future Enhancements

- Online Payment Gateway Integration
- Live Order Tracking
- Email Notifications
- OTP Authentication
- AI-Based Food Recommendations
- Delivery Partner Module
- REST API Integration
- Spring Boot Migration

---

# 🤝 Contributing

Contributions are welcome.

If you'd like to improve this project:

1. Fork the repository
2. Create a new feature branch
3. Commit your changes
4. Push your branch
5. Open a Pull Request

---

# 👨‍💻 Author

### Mohammed Furqaan

Computer Science Engineer

**GitHub**

https://github.com/Mohammed-Furqaan

**LinkedIn**

*Add your LinkedIn profile here.*

---

# 📄 License

This project is licensed under the **MIT License**.

---

# ⭐ Support

If you found this project useful, consider giving it a ⭐ on GitHub.

It helps others discover the project and motivates future improvements.
