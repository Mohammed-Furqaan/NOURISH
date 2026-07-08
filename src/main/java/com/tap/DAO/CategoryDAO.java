package com.tap.DAO;

import java.util.List;
import com.tap.model.Category;

public interface CategoryDAO {

    // Insert
    int addCategory(Category category);

    // Fetch
    Category getCategoryById(int categoryId);

    Category getCategoryByName(String categoryName);

    List<Category> getAllCategories();

    List<Category> getActiveCategories();

    // Update
    int updateCategory(Category category);

    // Delete
    int deleteCategory(int categoryId);
    
    int getCategoryCount();

}