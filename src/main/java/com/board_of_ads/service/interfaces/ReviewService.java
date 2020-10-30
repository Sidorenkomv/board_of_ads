package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.User;
import com.board_of_ads.models.dto.review.Review;

import java.util.Set;

public interface ReviewService {
    Review save(Review review);
    Set<Review> getReviewsByUser(User user);
    Set<Review> getReviewsByPostingOwner(User user);
}
