package com.example.hrmsystem.infrastructure.persistence.identity.jpa;

import com.example.hrmsystem.infrastructure.persistence.identity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserJpaRepository extends JpaRepository<User, UUID> {
}
