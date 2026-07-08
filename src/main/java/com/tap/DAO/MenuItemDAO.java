package com.tap.DAO;

import java.util.List;
import com.tap.model.MenuItem;

public interface MenuItemDAO {

    // Insert
    int addMenuItem(MenuItem menuItem);

    // Fetch
    MenuItem getMenuItemById(int itemId);

    List<MenuItem> getMenuItemsByRestaurantId(int restaurantId);

    List<MenuItem> getMenuItemsByCategoryId(int categoryId);

    List<MenuItem> getAvailableMenuItems();

    List<MenuItem> getAllMenuItems();

    // Update
    int updateMenuItem(MenuItem menuItem);

    // Delete
    int deleteMenuItem(int itemId);
    
    int getMenuItemCount();

}