package com.example.hrmsystem.infrastructure.persistence.identity.jpa;
import com.example.hrmsystem.infrastructure.persistence.identity.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleJpaRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByCode(String code);

}