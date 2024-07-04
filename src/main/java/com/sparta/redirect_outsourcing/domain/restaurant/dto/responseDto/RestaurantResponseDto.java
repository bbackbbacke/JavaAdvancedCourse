package com.sparta.redirect_outsourcing.domain.restaurant.dto.responseDto;

import com.sparta.redirect_outsourcing.domain.restaurant.entity.Restaurant;
import com.sparta.redirect_outsourcing.domain.restaurant.entity.RestaurntCategoryEnum;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class RestaurantResponseDto {
    private String name;

    private String address;

    private RestaurntCategoryEnum categoryEnum;

    private String description;

    private LocalDateTime createdAt;
    private int likeCount;

    public RestaurantResponseDto(Restaurant restaurant) {
        this.name = restaurant.getName();
        this.address = restaurant.getAddress();
        this.categoryEnum = restaurant.getCategory();
        this.description = restaurant.getDescription();
        this.createdAt = restaurant.getCreatedAt();

    }

    public static RestaurantResponseDto of(Restaurant restaurant, long likeCount) {
        return RestaurantResponseDto.builder()
            .name(restaurant.getName())
            .address(restaurant.getAddress())
            .categoryEnum(restaurant.getCategory())
            .description(restaurant.getDescription())
            .createdAt(restaurant.getCreatedAt())
            .likeCount(restaurant.getLikeCount())
            .build();

    }

}
