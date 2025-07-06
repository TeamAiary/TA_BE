package com.aiary.be.global.exception.errorCode;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum DiaryErrorCode implements ErrorCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "D001", "존재하지 않는 다이어리 아이디입니다."),
    NOT_MATCH(HttpStatus.UNAUTHORIZED, "D002", "자신의 다이어리만 열람할 수 있습니다"),
    ALREADY_EXIST_DAY(HttpStatus.BAD_REQUEST, "D003", "이미 다이어리를 작성한 날입니다.");
    
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
