package com.example.hrmsystem.domain.identity.exception;

public class InactiveUserException extends DomainAuthException {
    public InactiveUserException() {
        super("User is inactive");
    }
}
