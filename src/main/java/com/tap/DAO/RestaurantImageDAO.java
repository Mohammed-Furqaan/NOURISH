package com.tap.DAO;

import java.util.List;
import com.tap.model.RestaurantImage;

public interface RestaurantImageDAO {

    // Insert
    int addImage(RestaurantImage image);

    // Fetch
    RestaurantImage getImageById(int imageId);

    List<RestaurantImage> getImagesByRestaurantId(int restaurantId);

    List<RestaurantImage> getAllImages();

    // Update
    int updateImage(RestaurantImage image);

    // Delete
    int deleteImage(int imageId);

}