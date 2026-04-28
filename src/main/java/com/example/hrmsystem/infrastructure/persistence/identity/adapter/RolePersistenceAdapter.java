package com.example.hrmsystem.infrastructure.persistence.identity.adapter;

import com.example.hrmsystem.application.identity.port.out.RolePersistencePort;
import com.example.hrmsystem.domain.identity.model.RoleModel;
import com.example.hrmsystem.infrastructure.persistence.identity.entity.mapper.RoleEntityMapper;
import com.example.hrmsystem.infrastructure.persistence.identity.jpa.RoleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RolePersistenceAdapter implements RolePersistencePort {

    private final RoleJpaRepository roleJpaRepository;
    private final RoleEntityMapper roleEntityMapper;

    @Override
    public void save(RoleModel role) {
        roleJpaRepository.save(roleEntityMapper.toRoleEntity(role));
    }


    @Override
    public Optional<RoleModel> findById(UUID id) {
        return roleJpaRepository.findById(id)
                .map(roleEntityMapper::toRoleModel);
    }

    @Override
    public Optional<RoleModel> findByCode(String code) {
        return roleJpaRepository.findByCode(code)
                .map(roleEntityMapper::toRoleModel);
    }

    @Override
    public List<RoleModel> findAll() {
        return roleJpaRepository.findAll().stream()
                .map(roleEntityMapper::toRoleModel)
                .toList();
    }

    @Override
    public void deleteById(UUID Id){
        roleJpaRepository.deleteById(Id);
    }
}
