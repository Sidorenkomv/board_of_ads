package com.board_of_ads.repository;

import com.board_of_ads.models.User;
import com.board_of_ads.models.dto.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Set<Review> findAllByUser(User user);
    Set<Review> findAllByPostingUser(User user);
}
