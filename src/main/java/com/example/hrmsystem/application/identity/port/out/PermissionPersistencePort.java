package com.example.hrmsystem.application.identity.port.out;

import com.example.hrmsystem.domain.identity.model.PermissionModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PermissionPersistencePort {
    void save(PermissionModel permission);
    void deleteById(UUID Id);
    List<PermissionModel> findAll();
    Optional<PermissionModel> findById(UUID uuid);
    Optional<PermissionModel> findByCode(String code);
    List<PermissionModel> findByIdIn(List<UUID> uuids);
}
