package com.tap.test;

import com.tap.DAO.FavoriteDAO;
import com.tap.DAOImpl.FavoriteDAOImpl;
import com.tap.model.Favorite;

public class FavoriteDAOTest {

    public static void main(String[] args) {

        FavoriteDAO dao = new FavoriteDAOImpl();

        Favorite fav = new Favorite(2, 2); // user_id, restaurant_id

        int result = dao.addFavorite(fav);

        if (result > 0) {
            System.out.println("Favorite Added Successfully...");
        } else {
            System.out.println("Failed to Add Favorite...");
        }
    }
}