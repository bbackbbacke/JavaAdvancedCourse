package com.sparta.redirect_outsourcing.exception.custom.like;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;

public class LikeException extends RuntimeException {

    private final ResponseCodeEnum responseCodeEnum;

    public LikeException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum.getMessage());
        this.responseCodeEnum = responseCodeEnum;
    }

}
