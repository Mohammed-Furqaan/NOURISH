package com.tap.DAO;

import java.util.List;
import com.tap.model.Review;

public interface ReviewDAO {

    int addReview(Review review);

    Review getReviewById(int reviewId);

    List<Review> getReviewsByRestaurantId(int restaurantId);

    List<Review> getReviewsByUserId(int userId);

    List<Review> getAllReviews();

    int updateReview(Review review);

    int deleteReview(int reviewId);
}