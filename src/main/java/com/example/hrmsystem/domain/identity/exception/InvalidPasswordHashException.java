package com.example.hrmsystem.domain.identity.exception;

public class InvalidPasswordHashException extends DomainAuthException {
    public InvalidPasswordHashException() {
        super("Password hash cannot be empty");
    }
}
