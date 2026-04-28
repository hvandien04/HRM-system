package com.example.hrmsystem.domain.identity.exception;

public abstract class DomainAuthException extends RuntimeException {
    protected DomainAuthException(String message) {
        super(message);
    }

    protected DomainAuthException(String message, Throwable cause) {
        super(message, cause);
    }
}
