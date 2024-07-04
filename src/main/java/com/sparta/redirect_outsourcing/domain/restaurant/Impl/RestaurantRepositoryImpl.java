package com.sparta.redirect_outsourcing.domain.restaurant.Impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.redirect_outsourcing.domain.restaurant.entity.Restaurant;
import com.sparta.redirect_outsourcing.domain.restaurant.entity.QRestaurant;
import com.sparta.redirect_outsourcing.domain.like.entity.QLike;
import com.sparta.redirect_outsourcing.domain.review.entity.QReview;
import com.sparta.redirect_outsourcing.domain.restaurant.repository.RestaurantAdapter;
import com.sparta.redirect_outsourcing.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RestaurantRepositoryImpl extends RestaurantAdapter {
    private final JPAQueryFactory queryFactory;

    // JPAQueryFactory를 생성자를 통해 주입 받음
    public RestaurantRepositoryImpl(JPAQueryFactory queryFactory) {
        super();
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<Restaurant> findLikedRestaurantsByUserId(Long userId, Pageable pageable) {
        QRestaurant restaurant = QRestaurant.restaurant;
        QLike like = QLike.like;
        QReview review = QReview.review;

        List<Restaurant> content = queryFactory
                .selectFrom(restaurant)
                .join(review).on(review.restaurant.eq(restaurant))
                .join(like).on(like.review.eq(review))
                .where(like.user.id.eq(userId))
                .orderBy(like.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .selectFrom(restaurant)
                .join(review).on(review.restaurant.eq(restaurant))
                .join(like).on(like.review.eq(review))
                .where(like.user.id.eq(userId))
                .fetchCount();

        return new PageImpl<>(content, pageable, total);
    }

    public Page<Review> findLikedReviewsByUserId(Long userId, Pageable pageable) {
        QReview review = QReview.review;
        QLike like = QLike.like;

        List<Review> content = queryFactory
                .selectFrom(review)
                .join(like).on(like.review.eq(review))
                .where(like.user.id.eq(userId))
                .orderBy(like.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .selectFrom(review)
                .join(like).on(like.review.eq(review))
                .where(like.user.id.eq(userId))
                .fetchCount();

        return new PageImpl<>(content, pageable, total);
    }
}

