package com.tap.DAO;

import java.util.List;
import com.tap.model.Restaurant;

public interface RestaurantDAO {

    // Insert a new restaurant
    int addRestaurant(Restaurant restaurant);

    // Get restaurant by ID
    Restaurant getRestaurantById(int restaurantId);

    // Get all restaurants
    List<Restaurant> getAllRestaurants();

    // Get restaurants by owner
    List<Restaurant> getRestaurantsByOwnerId(int ownerId);

    // Update restaurant details
    int updateRestaurant(Restaurant restaurant);

    // Delete restaurant
    int deleteRestaurant(int restaurantId);
    
    List<Restaurant> searchRestaurants(String keyword);
    
    int getRestaurantCount();

}