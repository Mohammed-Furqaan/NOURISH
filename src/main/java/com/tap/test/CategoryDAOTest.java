package com.tap.test;

import com.tap.DAO.CategoryDAO;
import com.tap.DAOImpl.CategoryDAOImpl;
import com.tap.model.Category;

public class CategoryDAOTest {

    public static void main(String[] args) {

        CategoryDAO dao = new CategoryDAOImpl();

        Category category = new Category(

                "Pizza",
                "Delicious Italian Pizzas",
                "images/categories/pizza.png",
                true

        );

        int result = dao.addCategory(category);

        if (result > 0) {
            System.out.println("Category Added Successfully...");
        } else {
            System.out.println("Failed to Add Category...");
        }
    }
}