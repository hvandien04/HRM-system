package com.example.hrmsystem.infrastructure.security;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SecurityErrorCode {

    UNAUTHENTICATED(HttpStatus.UNAUTHORIZED, 401, "Unauthenticated");

    private final HttpStatus status;
    private final int code;
    private final String message;

    SecurityErrorCode(HttpStatus status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}