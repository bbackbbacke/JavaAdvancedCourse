package com.sparta.redirect_outsourcing.domain.restaurant.dto.responseDto;

import com.sparta.redirect_outsourcing.domain.restaurant.entity.Restaurant;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class LikedRestaurantResponseDto {
    private Long id;
    private String name;
    private String address;
    private String category;
    private String description;
    private int likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private LikedRestaurantResponseDto convertToDto(Restaurant restaurant) {
        return new LikedRestaurantResponseDto(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getCategory().name(),
                restaurant.getDescription(),
                restaurant.getLikeCount(),
                restaurant.getCreatedAt(),
                restaurant.getUpdatedAt()
        );
    }

    public LikedRestaurantResponseDto(Long id, String name, String address, String name1, String description, int likeCount, LocalDateTime createdAt, LocalDateTime updatedAt) {
    }
}
