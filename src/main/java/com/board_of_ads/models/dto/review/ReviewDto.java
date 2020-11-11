package com.board_of_ads.models.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReviewDto {
    private Long id;
    private ReviewUserDto userFrom;
    private ReviewUserDto userTo;
    private Byte rating;
    private String message;
    private LocalDateTime dateTime;

    public ReviewDto(Review review){
        this.id = review.getId();
        this.userFrom = new ReviewUserDto(review.getUser());
        this.userTo = new ReviewUserDto(review.getPosting().getUser());
        this.rating = review.getRating();
        this.message = review.getComment();
        this.dateTime = review.getDateTime();
    }
}
