package com.sparta.redirect_outsourcing.domain.review.dto;

import com.sparta.redirect_outsourcing.domain.review.entity.Review;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewResponseDto {
    private Float rating;
    private String comment;
    private int likeCount;

    public static ReviewResponseDto of(Review review, long likeCount) {
        return ReviewResponseDto.builder()
                .rating(review.getRating())
                .comment(review.getComment())
                .likeCount(review.getLikeCount())
                .build();
    }

    public static ReviewResponseDto of(Review review) {
        return ReviewResponseDto.builder()
            .rating(review.getRating())
            .comment(review.getComment())
            .likeCount(review.getLikeCount())
            .build();
    }

}