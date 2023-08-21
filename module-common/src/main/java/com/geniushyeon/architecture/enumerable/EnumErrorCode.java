package com.geniushyeon.architecture.enumerable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumErrorCode {

    SUCCESS(0, "SUCCESS"),
    TOKEN_REQUIRED(100001, "토큰이 없습니다."),
    TOKEN_EXPIRED(100002, "만료된 토큰입니다."),
    TOKEN_INVALID(100003, "유효하지 않은 토큰입니다."),

    MEMBER_NOT_FOUND(200001, "회원을 찾을 수 없습니다."),
    ;

    private final int result;
    private final String message;

}
