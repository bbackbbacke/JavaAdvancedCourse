package com.sparta.redirect_outsourcing.domain.like.service;

import com.sparta.redirect_outsourcing.domain.like.entity.Like;
import com.sparta.redirect_outsourcing.domain.like.repository.LikeAdapter;
import com.sparta.redirect_outsourcing.domain.review.entity.Review;
import com.sparta.redirect_outsourcing.domain.review.repository.ReviewAdapter;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import com.sparta.redirect_outsourcing.domain.user.repository.UserAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeAdapter likeAdapter;
    private final ReviewAdapter reviewAdapter;
    private final UserAdapter userAdapter;

    // 좋아요 달기
    @Transactional
    public void addLike(Long userId, Long reviewId) {
        User user = userAdapter.findById(userId);
        Review review = reviewAdapter.findById(reviewId); // 이미 reviewAdapter에서 .orElsethrow처리 함

        // 자신의 리뷰에 좋아요 할 수 없음
        if(review.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("자신의 리뷰에 좋아요를 할 수 없습니다.");
        }

        // 동일한 리뷰에 여러 번 좋아요를 누를 수 없도록 예외 처리(같은 게시물에는 사용자당 한 번만 좋아요가 가능)
        if (likeAdapter.existsByUserAndReview(user, review)) {
            throw new IllegalArgumentException("해당 리뷰에 이미 좋아요를 하셨습니다.");
        }
        Like like = new Like();
        like.setUser(user);
        like.setReview(review);

        review.setLikeCount(review.getLikeCount() + 1);

        Like savedLike = likeAdapter.saveLike(like);
    }

    // 좋아요 삭제
    @Transactional
    public void removeLike(Long userId, Long reviewId) {
        Review review = reviewAdapter.findById(reviewId);

        review.setLikeCount(review.getLikeCount() - 1);

        likeAdapter.deleteLike(userId, reviewId);
    }

    // 좋아요 수
    @Transactional(readOnly = true)
    public int countLikes(Long reviewId) {
        return likeAdapter.countLikes(reviewId);
    }
}

