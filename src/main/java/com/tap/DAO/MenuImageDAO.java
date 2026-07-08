package com.tap.DAO;

import java.util.List;
import com.tap.model.MenuItemImage;

public interface MenuImageDAO {

    // Insert
    int addImage(MenuItemImage image);

    // Fetch
    MenuItemImage getImageById(int imageId);

    List<MenuItemImage> getImagesByItemId(int itemId);

    List<MenuItemImage> getAllImages();

    // Update
    int updateImage(MenuItemImage image);

    // Delete
    int deleteImage(int imageId);

}