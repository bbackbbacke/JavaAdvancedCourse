package com.sparta.redirect_outsourcing.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResponseCodeEnum {
    // 유저
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다"),
    USER_DELETED(HttpStatus.UNAUTHORIZED, "탈퇴한 사용자입니다"),
    INVALID_TOKENS(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    INVALID_ADMIN_TOKEN(HttpStatus.FORBIDDEN, "관리자 암호가 틀려 등록이 불가능합니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    INVALID_CURRENT_PASSWORD(HttpStatus.BAD_REQUEST, "현재 비밀번호가 유효하지 않습니다."),
    SAME_AS_OLD_PASSWORD(HttpStatus.BAD_REQUEST, "새 비밀번호는 이전 비밀번호와 다르게 설정해야 합니다."),
    UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "프로필 사진 업로드 중 오류가 발생했습니다."),
    USER_NOT_MATCH(HttpStatus.FORBIDDEN, "이 작업을 수행할 권한이 없습니다. 로그인을 확인해 주세요."),

    //가게
    RESTAURANT_NOT_FOUND(HttpStatus.NOT_FOUND, "텅~ 가게를 찾을 수 없습니다."), //가게 조회시
    NOT_YOUR_RESTAURANT(HttpStatus.UNAUTHORIZED,"님의 가게가 아닙니다."),
    NOT_EXIST_CATEGORY(HttpStatus.NOT_FOUND, "올바른 형식을 입력해주십시오."),
    RESTAURANT_NOT_EXIST(HttpStatus.NOT_FOUND,"존재하지 않는 가게입니다."), //특정 가게에 대한 API요청 ex)찜하기

    // 주문
    CART_MENUS_NOT_FOUND(HttpStatus.NOT_FOUND, "장바구니의 메뉴를 찾을 수 없습니다."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 주문 내역을 찾을 수 없습니다."),

    // 리뷰
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND,"리뷰를 찾을 수 없습니다."),
    REVIEW_OVER_RATING(HttpStatus.BAD_REQUEST,"평점은 1에서5 사이 만 가능합니다."),

    // 좋아요
    CANT_LIKE_MYSELF(HttpStatus.FORBIDDEN, "자신의 리뷰에 좋아요를 할 수 없습니다."),
    ALREADY_LIKE(HttpStatus.BAD_REQUEST, "이미 좋아요한 리뷰입니다.") ,

    // 장바구니
    CART_NOT_FOUND(HttpStatus.NOT_FOUND, "장바구니를 찾을 수 없습니다."),
    CART_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "장바구니 목록을 찾을 수 없습니다."),

    //메뉴
    MENU_NOT_FOUND(HttpStatus.NOT_FOUND,"메뉴를 찾을 수 없습니다."),
    MENU_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND,"카테고리를 찾을 수 없습니다."),
    MENU_USER_NOT_MATCH(HttpStatus.FORBIDDEN,"다른 유저가 생성한 메뉴는 수정 삭제 할 수 없습니다."),

    //찜하기
    ALREADY_FOLLOWED(HttpStatus.MULTI_STATUS,"이미 찜한 가게입니다.")

    ;
    private final HttpStatus httpStatus;
    private final String message;
}