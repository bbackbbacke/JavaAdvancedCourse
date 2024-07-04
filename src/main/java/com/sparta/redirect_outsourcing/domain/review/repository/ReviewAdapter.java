package com.sparta.redirect_outsourcing.domain.review.repository;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import com.sparta.redirect_outsourcing.domain.review.entity.Review;
import com.sparta.redirect_outsourcing.exception.custom.review.ReviewException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewAdapter {
    private final ReviewRepository reviewRepository;

    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    public void delete(Review review){
        reviewRepository.delete(review);
    }

    public Review findById(Long id){
        return reviewRepository.findById(id)
                .orElseThrow(()-> new ReviewException(ResponseCodeEnum.REVIEW_NOT_FOUND));
    }

    public List<Review> findAll(){
        return reviewRepository.findAll();
    }

    public List<Review> findByRestaurantId(Long restaurantId){
        return reviewRepository.findAllByRestaurantId(restaurantId);
    }

    public long countLikesById(Long reviewId){
        return reviewRepository.countLikesById(reviewId);
    }

}
