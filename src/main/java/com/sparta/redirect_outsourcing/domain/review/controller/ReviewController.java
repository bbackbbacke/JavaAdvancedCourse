package com.sparta.redirect_outsourcing.domain.review.controller;

import com.sparta.redirect_outsourcing.auth.UserDetailsImpl;
import com.sparta.redirect_outsourcing.common.DataResponseDto;
import com.sparta.redirect_outsourcing.common.MessageResponseDto;
import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import com.sparta.redirect_outsourcing.common.ResponseUtils;
import com.sparta.redirect_outsourcing.domain.review.dto.ReviewRequestDto;
import com.sparta.redirect_outsourcing.domain.review.dto.ReviewResponseDto;
import com.sparta.redirect_outsourcing.domain.review.entity.Review;
import com.sparta.redirect_outsourcing.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/restaurants/{restaurantsId}/reviews")
    public ResponseEntity<DataResponseDto<ReviewResponseDto>> createReview(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody ReviewRequestDto requestDto,
            @PathVariable Long restaurantsId
    ){
        ReviewResponseDto responseDto = reviewService.createReview(userDetails.getUser(), requestDto, restaurantsId);
        return ResponseUtils.of(HttpStatus.OK, "리뷰 작성 성공",responseDto);
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<DataResponseDto<ReviewResponseDto>> updateReview(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody ReviewRequestDto requestDto,
            @PathVariable Long reviewId
    ){
        ReviewResponseDto responseDto = reviewService.updateReview(userDetails.getUser(), requestDto,reviewId);
        return ResponseUtils.of(HttpStatus.OK,"리뷰 수정 성공",responseDto);
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<MessageResponseDto> deleteReview(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long reviewId
    ){
        reviewService.deleteReview(userDetails.getUser(), reviewId);
        return ResponseUtils.of(HttpStatus.OK,"리뷰 삭제 성공");
    }

    @GetMapping("/restaurants/{restaurantsId}/reviews")
    public ResponseEntity<DataResponseDto<List<ReviewResponseDto>>>getReviewList(
            @PathVariable Long restaurantsId
    ){
        List<ReviewResponseDto> reviews = reviewService.getReviews(restaurantsId);
        return ResponseUtils.of(HttpStatus.OK,"리뷰 조회 성공",reviews);
    }

    @GetMapping("/reviews/liked")
    public ResponseEntity<DataResponseDto<Page<Review>>> findLikedReviewsByUserId(@RequestParam Long userId, Pageable pageable) {
        Page<Review> reviews = reviewService.findLikedReviewsByUserId(userId, pageable);
        return ResponseUtils.of(HttpStatus.OK, "좋아요 단 리뷰 조회 성공", reviews);
    }

}
