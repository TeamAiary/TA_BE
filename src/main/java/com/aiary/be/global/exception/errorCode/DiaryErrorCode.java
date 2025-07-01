package com.aiary.be.global.exception.errorCode;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum DiaryErrorCode implements ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "D001", "존재하지 않는 다이어리 아이디입니다.");
    
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
