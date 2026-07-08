package com.tap.DAO;

import java.util.List;
import com.tap.model.Favorite;

public interface FavoriteDAO {

    int addFavorite(Favorite favorite);

    Favorite getFavoriteById(int favoriteId);

    List<Favorite> getFavoritesByUserId(int userId);

    List<Favorite> getAllFavorites();

    int deleteFavorite(int favoriteId);
    
    int deleteFavoriteByUserAndRestaurant(int userId, int restaurantId);
}