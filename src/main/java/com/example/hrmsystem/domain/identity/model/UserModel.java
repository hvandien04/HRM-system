package com.example.hrmsystem.domain.identity.model;

import com.example.hrmsystem.domain.identity.exception.InactiveUserException;
import com.example.hrmsystem.domain.identity.value.PasswordHash;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class UserModel {
    UUID id;
    String username;
    PasswordHash passwordHash;
    Boolean isActive;
    Instant createdAt;
    List<RoleModel> roles;


    public UserModel login(Instant now) {
        if (!isActive) {
            throw new InactiveUserException();
        }
        return this;
    }
}
