package com.tap.servlet;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.tap.DAO.CategoryDAO;
import com.tap.DAOImpl.CategoryDAOImpl;
import com.tap.model.Category;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/adminCategories")
public class AdminCategoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CategoryDAO categoryDAO;

    @Override
    public void init() throws ServletException {
        categoryDAO = new CategoryDAOImpl();
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
        List<Category> categoryList = categoryDAO.getAllCategories();
        if (keyword != null && !keyword.trim().isEmpty()) {
            final String kw = keyword.toLowerCase().trim();
            categoryList = categoryList.stream()
                .filter(c -> c.getCategoryName().toLowerCase().contains(kw) || 
                             (c.getDescription() != null && c.getDescription().toLowerCase().contains(kw)))
                .collect(Collectors.toList());
        }
        request.setAttribute("categoryList", categoryList);

        if ("edit".equalsIgnoreCase(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Category editCategory = categoryDAO.getCategoryById(id);
                request.setAttribute("editCategory", editCategory);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("delete".equalsIgnoreCase(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                categoryDAO.deleteCategory(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.sendRedirect("adminCategories");
            return;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("adminCategories.jsp");
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
        String categoryName = request.getParameter("categoryName");
        String description = request.getParameter("description");
        
        String categoryImage = request.getParameter("categoryImage");
        if (categoryImage != null && categoryImage.trim().isEmpty()) {
            categoryImage = null;
        }
        
        boolean isActive = "true".equalsIgnoreCase(request.getParameter("isActive")) || "on".equalsIgnoreCase(request.getParameter("isActive"));

        if ("add".equalsIgnoreCase(action)) {
            Category c = new Category(categoryName, description, categoryImage, isActive);
            categoryDAO.addCategory(c);
        } else if ("update".equalsIgnoreCase(action)) {
            try {
                int categoryId = Integer.parseInt(request.getParameter("categoryId"));
                Category c = new Category();
                c.setCategoryId(categoryId);
                c.setCategoryName(categoryName);
                c.setDescription(description);
                c.setCategoryImage(categoryImage);
                setActiveHelper(c, isActive);
                categoryDAO.updateCategory(c);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect("adminCategories");
    }

    // Workaround helper to set active status matching standard models
    private void setActiveHelper(Category c, boolean active) {
        c.setActive(active);
    }
}
