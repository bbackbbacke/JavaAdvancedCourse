package com.sparta.redirect_outsourcing.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProfileResponseDto {
    private String nickname;
    private String introduce;
    private String pictureUrl;
    private long likedPostsCount;
    private long likedCommentsCount;
}
