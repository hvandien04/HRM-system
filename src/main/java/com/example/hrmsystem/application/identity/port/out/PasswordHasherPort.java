package com.example.hrmsystem.application.identity.port.out;

public interface PasswordHasherPort {
    String hash(String rawPassword);
    boolean matches(String rawPassword, String passwordHash);
}
