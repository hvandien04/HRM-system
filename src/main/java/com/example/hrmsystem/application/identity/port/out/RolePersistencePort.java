package com.example.hrmsystem.application.identity.port.out;


import com.example.hrmsystem.domain.identity.model.RoleModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RolePersistencePort {
    void save(RoleModel RoleModel);
    void deleteById(UUID Id);
    Optional<RoleModel> findById(UUID id);
    Optional<RoleModel> findByCode(String code);
    List<RoleModel> findAll();
}