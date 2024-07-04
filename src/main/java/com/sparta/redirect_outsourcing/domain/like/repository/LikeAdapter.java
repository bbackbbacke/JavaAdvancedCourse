package com.sparta.redirect_outsourcing.domain.like.repository;

import com.sparta.redirect_outsourcing.domain.like.entity.Like;
import com.sparta.redirect_outsourcing.domain.review.entity.Review;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LikeAdapter {

    private final LikeRepository likeRepository;

    public Like saveLike (Like like) {
        return likeRepository.save(like);
    }

    public void deleteLike(Long userId, Long reviewId) {
        likeRepository.deleteByUserIdAndReviewId(userId, reviewId);
    }

    public int countLikes(Long reviewId) {
        return likeRepository.countByReviewId(reviewId);
    }

    public boolean existsByUserAndReview(User user, Review review) {
        return likeRepository.existsByUserAndReview(user, review);
    }

    public Page<Like> findByUserId(Long userId, Pageable pageable) {
        return likeRepository.findByUserId(userId, pageable);
    }
}
