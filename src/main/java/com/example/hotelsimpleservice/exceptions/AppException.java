package com.example.hotelsimpleservice.exceptions;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {
    private final ErrorCode errorCode;
    private final Object[] params;

    public AppException(ErrorCode errorCode, Object... params) {
        this.errorCode = errorCode;
        this.params = params;
    }
}