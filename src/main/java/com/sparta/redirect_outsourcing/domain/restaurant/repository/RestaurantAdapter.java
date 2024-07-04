package com.sparta.redirect_outsourcing.domain.restaurant.repository;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import com.sparta.redirect_outsourcing.domain.restaurant.dto.responseDto.LikedRestaurantResponseDto;
import com.sparta.redirect_outsourcing.domain.restaurant.entity.Restaurant;
import com.sparta.redirect_outsourcing.exception.custom.restaurant.RestaurantNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public abstract class RestaurantAdapter {

    private final RestaurantRepository restaurantRepository;

    public Restaurant findById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId).orElseThrow(() -> new RestaurantNotFoundException(ResponseCodeEnum.RESTAURANT_NOT_EXIST));
    }

    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public void delete(Restaurant restaurant) {
        restaurantRepository.delete(restaurant);
    }

    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    public long countLikesById(Long restaurantId){
        return restaurantRepository.countLikesById(restaurantId);
    }

    public Page<Restaurant> findByLikedByUserId(Long userId, Pageable pageable) {
        return restaurantRepository.findLikedRestaurantsByUserId(userId, pageable);
    }


    public abstract Page<Restaurant> findLikedRestaurantsByUserId(Long userId, Pageable pageable);
}
