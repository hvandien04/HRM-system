package com.example.hrmsystem.infrastructure.persistence.identity.entity.mapper;

import com.example.hrmsystem.domain.identity.model.RoleModel;
import com.example.hrmsystem.infrastructure.persistence.identity.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleEntityMapper {

    Role toRoleEntity(RoleModel role);

    RoleModel toRoleModel(Role role);

}
