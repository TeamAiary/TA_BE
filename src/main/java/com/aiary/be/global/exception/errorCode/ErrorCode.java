package com.aiary.be.global.exception.errorCode;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    HttpStatus httpStatus();
    
    String code();
    
    String message();
}
