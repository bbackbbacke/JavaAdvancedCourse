package com.sparta.redirect_outsourcing.exception.custom.like;

import com.sparta.redirect_outsourcing.common.ResponseCodeEnum;

public class CantLikeMyselfException extends LikeException{
    public CantLikeMyselfException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum);
    }

}
