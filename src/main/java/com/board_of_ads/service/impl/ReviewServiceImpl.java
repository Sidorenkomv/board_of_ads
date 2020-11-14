package com.board_of_ads.service.impl;

import com.board_of_ads.models.User;
import com.board_of_ads.models.dto.review.Review;
import com.board_of_ads.repository.ReviewRepository;
import com.board_of_ads.service.interfaces.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {
    ReviewRepository reviewRepository;

    @Override
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Set<Review> getReviewsByUser(User user) {
        return reviewRepository.findAllByUser(user);
    }

    @Override
    public Set<Review> getReviewsByPostingOwner(User user) {
        return reviewRepository.findAllByPostingUser(user);
    }


}
