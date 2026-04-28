package com.example.hrmsystem.application.identity.service;

import com.example.hrmsystem.application.identity.command.RegisterUserCommand;
import com.example.hrmsystem.application.identity.dto.UserProfileDto;
import com.example.hrmsystem.application.identity.exception.AuthErrorCode;
import com.example.hrmsystem.application.identity.port.in.RegisterUserUseCase;
import com.example.hrmsystem.application.identity.port.out.PasswordHasherPort;
import com.example.hrmsystem.application.identity.port.out.UserPersistencePort;
import com.example.hrmsystem.domain.identity.exception.InvalidPasswordException;
import com.example.hrmsystem.domain.identity.model.UserModel;
import com.example.hrmsystem.domain.identity.value.PasswordHash;
import com.example.hrmsystem.domain.identity.value.PlainPassword;
import com.example.hrmsystem.interfaces.exception.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterUserService implements RegisterUserUseCase {

    private final UserPersistencePort userPersistencePort;
    private final PasswordHasherPort passwordHasherPort;

    @Override
    public UserProfileDto register(RegisterUserCommand command) {
        if (userPersistencePort.existsByUsername(command.getUsername())) {
            throw new AppException(AuthErrorCode.USERNAME_TAKEN, AuthErrorCode.USERNAME_TAKEN.getDefaultMessage());
        }

        PasswordHash passwordHash;
        try {
            PlainPassword plainPassword = new PlainPassword(command.getPassword());
            passwordHash = new PasswordHash(passwordHasherPort.hash(plainPassword.password()));
        } catch (InvalidPasswordException ex) {
            throw new AppException(AuthErrorCode.INVALID_PASSWORD, AuthErrorCode.INVALID_PASSWORD.getDefaultMessage());
        }

        UserModel user = UserModel.builder()
                .id(UUID.randomUUID())
                .username(command.getUsername())
                .passwordHash(passwordHash)
                .isActive(true)
                .createdAt(Instant.now())
                .roles(List.of())
                .build();

        userPersistencePort.save(user);

        return UserProfileDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }
}
