package com.example.hrmsystem.infrastructure.persistence.identity.adapter;

import com.example.hrmsystem.application.identity.port.out.PermissionPersistencePort;
import com.example.hrmsystem.domain.identity.model.PermissionModel;
import com.example.hrmsystem.infrastructure.persistence.identity.entity.mapper.PermissionEntityMapper;
import com.example.hrmsystem.infrastructure.persistence.identity.jpa.PermissionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PermissionPersistenceAdapter implements PermissionPersistencePort {

    private final PermissionJpaRepository permissionJpaRepository;
    private final PermissionEntityMapper permissionEntityMapper;

    @Override
    public void save(PermissionModel permission) {
        permissionJpaRepository.save(permissionEntityMapper.toPermissionEntity(permission));
    }

    @Override
    public void deleteById(UUID Id) {
        permissionJpaRepository.deleteById(Id);
    }

    @Override
    public List<PermissionModel> findAll() {
        return permissionJpaRepository.findAll().stream()
                .map(permissionEntityMapper::toPermissionModel)
                .toList();
    }

    @Override
    public Optional<PermissionModel> findById(UUID uuid) {
        return permissionJpaRepository.findById(uuid)
                .map(permissionEntityMapper::toPermissionModel);
    }


    @Override
    public Optional<PermissionModel> findByCode(String code) {
        return permissionJpaRepository.findByCode(code)
                .map(permissionEntityMapper::toPermissionModel);
    }

    @Override
    public List<PermissionModel> findByIdIn(List<UUID> uuids) {
        return permissionJpaRepository.findByIdIn(uuids).stream()
                .map(permissionEntityMapper::toPermissionModel)
                .toList();
    }
}