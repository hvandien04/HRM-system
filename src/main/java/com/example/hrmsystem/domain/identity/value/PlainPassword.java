package com.example.hrmsystem.domain.identity.value;

import com.example.hrmsystem.domain.identity.exception.InvalidPasswordException;

public record PlainPassword(String password) {
    private static final String POLICY =
            "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)\\S{8,}$";

    public PlainPassword {
        if (password == null || !password.matches(POLICY)) {
            throw new InvalidPasswordException();
        }
    }
}