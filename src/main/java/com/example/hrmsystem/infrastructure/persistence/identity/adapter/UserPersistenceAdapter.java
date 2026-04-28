package com.example.hrmsystem.infrastructure.persistence.identity.adapter;

import com.example.hrmsystem.application.identity.port.out.UserPersistencePort;
import com.example.hrmsystem.domain.identity.model.UserModel;
import com.example.hrmsystem.infrastructure.persistence.identity.entity.mapper.UserEntityMapper;
import com.example.hrmsystem.infrastructure.persistence.identity.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {

    private final UserJpaRepository appUserJpaRepository;
    private final UserEntityMapper appUserMapper;

    @Override
    public Optional<UserModel> findByUsername(String username) {
        return (appUserJpaRepository.findByUsername(username)
                .map(appUserMapper::toDto));
    }

    @Override
    public boolean existsByUsername(String username) {
        return appUserJpaRepository.existsByUsername(username);
    }

    @Override
    public void save(UserModel user) {
        appUserJpaRepository.save(appUserMapper.toEntity(user));
    }

    @Override
    public UserModel findById(UUID id) {
        return appUserMapper.toDto(appUserJpaRepository.findById(id).orElseThrow());
    }

}