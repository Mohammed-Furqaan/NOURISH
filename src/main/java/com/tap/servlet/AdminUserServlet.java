package com.tap.servlet;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.tap.DAO.UserDAO;
import com.tap.DAOImpl.UserDAOImpl;
import com.tap.model.User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/adminUsers")
public class AdminUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAOImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect("adminLogin.jsp");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        String keyword = request.getParameter("keyword");
        List<User> userList = userDAO.getAllUsers();
        if (keyword != null && !keyword.trim().isEmpty()) {
            final String kw = keyword.toLowerCase().trim();
            userList = userList.stream()
                .filter(u -> u.getFirstName().toLowerCase().contains(kw) || 
                             u.getLastName().toLowerCase().contains(kw) || 
                             u.getEmail().toLowerCase().contains(kw) || 
                             (u.getPhoneNumber() != null && u.getPhoneNumber().contains(kw)) ||
                             u.getRole().toLowerCase().contains(kw))
                .collect(Collectors.toList());
        }
        request.setAttribute("userList", userList);

        if ("edit".equalsIgnoreCase(action) || "view".equalsIgnoreCase(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                User editUser = userDAO.getUserById(id);
                request.setAttribute("editUser", editUser);
                request.setAttribute("viewMode", "view".equalsIgnoreCase(action));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("delete".equalsIgnoreCase(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                userDAO.deleteUser(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.sendRedirect("adminUsers");
            return;
        } else if ("status".equalsIgnoreCase(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                String newStatus = request.getParameter("status");
                User u = userDAO.getUserById(id);
                if (u != null) {
                    u.setAccountStatus(newStatus);
                    userDAO.updateUser(u);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.sendRedirect("adminUsers");
            return;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("adminUsers.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect("adminLogin.jsp");
            return;
        }

        String action = request.getParameter("action");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        
        String profileImage = request.getParameter("profileImage");
        if (profileImage != null && profileImage.trim().isEmpty()) {
            profileImage = null;
        }
        
        String role = request.getParameter("role");
        if (role == null || role.trim().isEmpty()) {
            role = "CUSTOMER";
        }
        
        String accountStatus = request.getParameter("accountStatus");
        if (accountStatus == null || accountStatus.trim().isEmpty()) {
            accountStatus = "ACTIVE";
        }

        boolean isVerified = "true".equalsIgnoreCase(request.getParameter("isVerified")) || "on".equalsIgnoreCase(request.getParameter("isVerified"));

        System.out.println("[AdminUserServlet] Action: " + action + ", Email: " + email + ", Phone: " + phoneNumber);

        if ("add".equalsIgnoreCase(action)) {
            String password = request.getParameter("password");
            if (password == null || password.trim().isEmpty()) {
                password = "Password@123";
            }
            User u = new User(firstName, lastName, email, password, phoneNumber, role, profileImage, isVerified, accountStatus);
            int res = userDAO.addUser(u);
            System.out.println("[AdminUserServlet] addUser result: " + res);
            if (res > 0) {
                session.setAttribute("userSuccess", "User added successfully!");
            } else {
                session.setAttribute("userError", "Failed to add user. Check console logs for DB exceptions (likely duplicate email/phone).");
            }
        } else if ("update".equalsIgnoreCase(action)) {
            try {
                int userId = Integer.parseInt(request.getParameter("userId"));
                User u = userDAO.getUserById(userId);
                if (u != null) {
                    u.setFirstName(firstName);
                    u.setLastName(lastName);
                    u.setEmail(email);
                    u.setPhoneNumber(phoneNumber);
                    u.setProfileImage(profileImage);
                    u.setAccountStatus(accountStatus);
                    u.setVerified(isVerified);
                    int res = userDAO.updateUser(u);
                    System.out.println("[AdminUserServlet] updateUser result: " + res);
                    if (res > 0) {
                        session.setAttribute("userSuccess", "User updated successfully!");
                    } else {
                        session.setAttribute("userError", "Failed to update user in database.");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("userError", "Error: " + e.getMessage());
            }
        }

        response.sendRedirect("adminUsers");
    }
}
