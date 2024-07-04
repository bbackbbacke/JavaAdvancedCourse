package com.sparta.redirect_outsourcing.domain.restaurant.entity;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;
import com.sparta.redirect_outsourcing.common.TimeStampEntity;
import com.sparta.redirect_outsourcing.domain.menu.entity.Menu;
import com.sparta.redirect_outsourcing.domain.restaurant.dto.requestDto.RestaurantCreateRequestDto;
import com.sparta.redirect_outsourcing.domain.restaurant.dto.requestDto.RestaurantUpdateRequestDto;
import com.sparta.redirect_outsourcing.domain.review.entity.Review;
import com.sparta.redirect_outsourcing.domain.user.entity.User;
import com.sparta.redirect_outsourcing.exception.custom.restaurant.NotExistRestaurantCategoryException;
import com.sparta.redirect_outsourcing.exception.custom.restaurant.NotYourRestaurantException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

@Getter
@Table(name = "restaurants")
@Entity
@NoArgsConstructor
public class Restaurant extends TimeStampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Menu> menus = new ArrayList<>();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RestaurntCategoryEnum category;

    private String description;

    @Setter
    @Column(nullable = false)
    private int likeCount;

    public Restaurant(User user, String name, String address, RestaurntCategoryEnum category, String description) {
        this.user = user;
        this.name = name;
        this.address = address;
        this.category = category;
        this.description = description;
    }

    public Restaurant(RestaurantCreateRequestDto createReq, User user) {
        this.user = user;
        this.name = createReq.getName();
        this.address = createReq.getAddress();
        this.category = enumCheck(createReq.getCategory());
        this.description = createReq.getDescription();
    }

    public void update(RestaurantUpdateRequestDto updateReq) {
        this.name = updateReq.getName();
        this.address = updateReq.getAddress();
        this.category = enumCheck(updateReq.getCategory());
        this.description = updateReq.getDescription();
    }
    //예외처리 카테고리 양식에 맞지 않을 경우
    private RestaurntCategoryEnum enumCheck(String reqCategory) {
        String categoryName= RestaurntCategoryEnum.checkCategory(reqCategory);

        if(categoryName!=null)
            return RestaurntCategoryEnum.valueOf(categoryName);
        else
            throw new NotExistRestaurantCategoryException(ResponseCodeEnum.NOT_EXIST_CATEGORY);
    }

    public void verify(User user) {
        if (!user.getUsername().equals(this.user.getUsername())) {
            throw new NotYourRestaurantException(ResponseCodeEnum.NOT_YOUR_RESTAURANT,user);
        }
    }
}