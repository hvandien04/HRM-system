package com.example.hrmsystem.domain.identity.value;

import com.example.hrmsystem.domain.identity.exception.InvalidPasswordHashException;

import lombok.Getter;

@Getter
public class PasswordHash {
    String passwordHash;


    public PasswordHash(String passwordHash) {
        if (passwordHash == null || passwordHash.isBlank())
            throw new InvalidPasswordHashException();
        this.passwordHash = passwordHash;
    }

    @Override
    public String toString() {
        return "****";
    }

}