package com.sparta.redirect_outsourcing.domain.review.service;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import com.sparta.redirect_outsourcing.domain.restaurant.entity.Restaurant;
import com.sparta.redirect_outsourcing.domain.restaurant.repository.RestaurantAdapter;
import com.sparta.redirect_outsourcing.domain.review.dto.ReviewRequestDto;
import com.sparta.redirect_outsourcing.domain.review.dto.ReviewResponseDto;
import com.sparta.redirect_outsourcing.domain.review.entity.Review;
import com.sparta.redirect_outsourcing.domain.review.repository.ReviewAdapter;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import com.sparta.redirect_outsourcing.exception.custom.review.ReviewOverRatingException;
import com.sparta.redirect_outsourcing.exception.custom.user.UserNotMatchException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewAdapter reviewAdapter;
    private final RestaurantAdapter restaurantAdapter;

    @Transactional
    public ReviewResponseDto createReview(User user, ReviewRequestDto requestDto, Long restaurantsId){
        if(requestDto.getRating()<1 || requestDto.getRating()>5){
            throw new ReviewOverRatingException(ResponseCodeEnum.REVIEW_OVER_RATING);
        }

        Restaurant findRestaurant = restaurantAdapter.findById(restaurantsId);

        Review review = new Review(requestDto.getRating(), requestDto.getComment(), user, findRestaurant);
        Review savedReview = reviewAdapter.save(review);
        return ReviewResponseDto.of(savedReview);
    }

    @Transactional
    public ReviewResponseDto updateReview(User user, ReviewRequestDto requestDto , Long reviewId){
        if(requestDto.getRating()<1 || requestDto.getRating()>5){
            throw new ReviewOverRatingException(ResponseCodeEnum.REVIEW_OVER_RATING);
        }
        Review review = reviewAdapter.findById(reviewId);
        if (!Objects.equals(review.getUser().getId(), user.getId())){
            throw new UserNotMatchException(ResponseCodeEnum.USER_NOT_FOUND);
        }
        review.update(requestDto);
        return ReviewResponseDto.of(review);
    }

    @Transactional
    public void deleteReview(User user, Long reviewId){
        Review review = reviewAdapter.findById(reviewId);
        if (!Objects.equals(review.getUser().getId(), user.getId())){
            throw new UserNotMatchException(ResponseCodeEnum.USER_NOT_FOUND);
        }
        reviewAdapter.delete(review);
    }

    // 리뷰들 조회
    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getReviews(Long restaurantsId){
        List<Review> reviews = reviewAdapter.findByRestaurantId(restaurantsId);
        List<ReviewResponseDto> responseReviews = new ArrayList<>();
        for (Review review : reviews) {
            responseReviews.add(ReviewResponseDto.of(review));
        }
        return responseReviews;
    }

    // 리뷰 단건 조회시, 개시글의 좋아요 개수 필드 추가
    @Transactional(readOnly = true)
    public ReviewResponseDto getReview(Long reviewId){
        Review review = reviewAdapter.findById(reviewId);
        long likeCount = reviewAdapter.countLikesById(reviewId);
        return ReviewResponseDto.of(review, likeCount);
    }




}