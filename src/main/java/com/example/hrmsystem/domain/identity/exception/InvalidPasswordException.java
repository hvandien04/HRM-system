package com.example.hrmsystem.domain.identity.exception;

public class InvalidPasswordException extends DomainAuthException {
    public InvalidPasswordException() {
        super("Password must be at least 8 characters and contain upper, lower, and digit characters");
    }
}
