package com.aiary.be.global.exception.errorCode;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties.Http;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "존재하지 않는 유저 아이디입니다."),
    INVALID_EMAIL_PASSWORD(HttpStatus.UNAUTHORIZED, "U002", "잘못된 이메일, 비밀번호 조합입니다."),
    REQUIRED_LOGIN(HttpStatus.UNAUTHORIZED, "U003", "로그인이 필요합니다.");
    
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
    
    @Override
    public HttpStatus httpStatus() {
        return this.httpStatus;
    }
    
    @Override
    public String code() {
        return this.code;
    }
    
    @Override
    public String message() {
        return this.message;
    }
}
