package com.sparta.redirect_outsourcing.domain.restaurant.Impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.redirect_outsourcing.domain.restaurant.entity.Restaurant;
import com.sparta.redirect_outsourcing.domain.restaurant.repository.RestaurantAdapter;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class RestaurantRepositoryImpl extends RestaurantAdapter {
    private final JPAQueryFactory queryFactory;

    public RestaurantRepositoryImpl(EntityManager em) {
        super();
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Restaurant> findLikedRestaurantsByUserId(Long userId, Pageable pageable) {
        // QueryDSL을 사용한 쿼리 구현
        QRestaurant restaurant = QRestaurant.restaurant;
        QLike like = QLike.like;
        QReview review = QReview.review;

        List<RestaurantDto> content = queryFactory
                .select(Projections.constructor(RestaurantDto.class,
                        restaurant.id,
                        restaurant.name,
                        restaurant.address,
                        restaurant.category,
                        restaurant.description,
                        restaurant.createdAt,
                        restaurant.updatedAt))
                .from(restaurant)
                .join(like).on(like.reviewId.eq(review.id))
                .join(review).on(review.restaurantId.eq(restaurant.id))
                .where(like.userId.eq(userId))
                .orderBy(like.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .selectFrom(restaurant)
                .join(like).on(like.reviewId.eq(review.id))
                .join(review).on(review.restaurantId.eq(restaurant.id))
                .where(like.userId.eq(userId))
                .fetchCount();

        return new PageImpl<>(content, pageable, total);
    }
}