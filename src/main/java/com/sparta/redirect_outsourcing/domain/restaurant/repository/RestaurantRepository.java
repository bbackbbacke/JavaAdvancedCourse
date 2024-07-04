package com.sparta.redirect_outsourcing.domain.restaurant.repository;

import com.sparta.redirect_outsourcing.domain.restaurant.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {
    Restaurant findRestaurantByName(String name);
    long countLikesById(Long restaurantId);

    Page<Restaurant> findByLikedByUserId(Long userId, Pageable pageable);

}
