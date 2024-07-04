package com.sparta.redirect_outsourcing.domain.like.repository;

import com.sparta.redirect_outsourcing.domain.like.entity.Like;
import com.sparta.redirect_outsourcing.domain.review.entity.Review;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    int countByReviewId(Long reviewId);
    void deleteByUserIdAndReviewId(Long userId, Long reviewId);
    boolean existsByUserAndReview(User user, Review review);
    Page<Like> findByUserId(Long userId, Pageable pageable);
    long countByUserIdAndReviewIsNotNull(Long userId);
    long countByUserIdAndCommentIsNotNull(Long userId);
}
