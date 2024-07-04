package com.sparta.redirect_outsourcing.domain.restaurant.service;

import com.sparta.redirect_outsourcing.domain.restaurant.dto.requestDto.RestaurantCreateRequestDto;
import com.sparta.redirect_outsourcing.domain.restaurant.dto.requestDto.RestaurantUpdateRequestDto;
import com.sparta.redirect_outsourcing.domain.restaurant.dto.responseDto.LikedRestaurantResponseDto;
import com.sparta.redirect_outsourcing.domain.restaurant.dto.responseDto.RestaurantResponseDto;
import com.sparta.redirect_outsourcing.domain.restaurant.entity.Restaurant;
import com.sparta.redirect_outsourcing.domain.restaurant.repository.RestaurantAdapter;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class RestaurantService {
    private final RestaurantAdapter restaurantAdapter;

    /************가게 등록*************/
    @Transactional
    public RestaurantResponseDto createRestaurant(RestaurantCreateRequestDto createReq, User user) {
        Restaurant restaurant = new Restaurant(createReq,user);
        restaurant = restaurantAdapter.save(restaurant);
        return new RestaurantResponseDto(restaurant);
    }

    /************전체 가게 조회*************/
    public List<RestaurantResponseDto> getRestaurants() {
        return restaurantAdapter.findAll().stream().map(RestaurantResponseDto::new).toList();
    }

    /************가게 단건 조회시, 가게의 좋아요 개수 필드 추가*************/
    public RestaurantResponseDto getOneRestaurant(Long restaurantId) {
        Restaurant restaurant = restaurantAdapter.findById(restaurantId);
        long likeCount = restaurantAdapter.countLikesById(restaurantId);
        return RestaurantResponseDto.of(restaurant, likeCount);
    }

    /************가게 정보 변경*************/
    @Transactional
    public RestaurantResponseDto updateRestaurant(Long restaurantId, RestaurantUpdateRequestDto updateReq, User user) {
        Restaurant restaurant = restaurantAdapter.findById(restaurantId);
        restaurant.verify(user);
        restaurant.update(updateReq);
        return new RestaurantResponseDto(restaurant);

    }

    /************가게 삭제*************/
    @Transactional
    public RestaurantResponseDto deleteRestaurant(Long restaurantId,User user) {
        Restaurant restaurant = restaurantAdapter.findById(restaurantId);
        restaurant.verify(user);
        restaurantAdapter.delete(restaurant);
        return new RestaurantResponseDto(restaurant);
    }

    private LikedRestaurantResponseDto convertToDto(Restaurant restaurant) {
        return LikedRestaurantResponseDto.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .description(restaurant.getDescription())
                .likeCount(restaurant.getLikeCount())
                .build();
    }

    public Page<LikedRestaurantResponseDto> getLikedRestaurants(Long userId, Pageable pageable) {
        Page<Restaurant> likedRestaurants = restaurantAdapter.findLikedRestaurantsByUserId(userId, pageable);
        return likedRestaurants.map(this::convertToDto);
    }
}
