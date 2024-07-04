package com.sparta.redirect_outsourcing.domain.review.repository;

import com.sparta.redirect_outsourcing.domain.review.entity.Review;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByRestaurantId(Long restaurantId);

    long countLikesById(Long reviewId); // review의 Id는 Id라고 되어있지 reviewId라고 안되어있잖아.. 그니까 엔티티에 어떻게 명시되어있는지 보고 그대로해야지 지영아 혼날래?
    
    Page<Review> findLikedReviewsByUserId(Long userId, Pageable pageable);


}