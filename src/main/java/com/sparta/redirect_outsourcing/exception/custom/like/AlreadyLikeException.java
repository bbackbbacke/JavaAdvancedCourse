package com.sparta.redirect_outsourcing.exception.custom.like;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;

public class AlreadyLikeException extends LikeException{

    public AlreadyLikeException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum);
    }
}
