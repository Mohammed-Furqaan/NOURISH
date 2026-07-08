package com.tap.test;

import com.tap.DAO.ReviewDAO;
import com.tap.DAOImpl.ReviewDAOImpl;
import com.tap.model.Review;

public class ReviewDAOTest {

    public static void main(String[] args) {

        ReviewDAO dao = new ReviewDAOImpl();

        Review review = new Review(
                2,              // user_id
                2,              // restaurant_id
                1,              // order_id (must exist)
                5,              // rating (1–5)
                "Amazing food and fast delivery!"
        );

        int result = dao.addReview(review);

        if (result > 0) {
            System.out.println("Review Added Successfully...");
        } else {
            System.out.println("Failed to Add Review...");
        }
    }
}