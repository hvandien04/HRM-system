package com.example.hrmsystem.application.identity.port.out;

import com.example.hrmsystem.domain.identity.model.UserModel;

import java.util.Optional;
import java.util.UUID;

public interface UserPersistencePort {
    Optional<UserModel> findByUsername(String username);
    boolean existsByUsername(String username);
    void save(UserModel user);
    UserModel findById(UUID id);
}
