package com.example.hrmsystem.infrastructure.persistence.identity.jpa;

import com.example.hrmsystem.infrastructure.persistence.identity.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PermissionJpaRepository extends JpaRepository<Permission, UUID> {
    Optional<Permission> findByCode(String code);

    List<Permission> findByIdIn(List<UUID> ids);
}