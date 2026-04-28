package com.example.hrmsystem.infrastructure.persistence.identity.entity.mapper;

import com.example.hrmsystem.domain.identity.model.PermissionModel;
import com.example.hrmsystem.infrastructure.persistence.identity.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PermissionEntityMapper {
    @Mapping(target = "roles", ignore = true)
    Permission toPermissionEntity(PermissionModel permission);

    PermissionModel toPermissionModel(Permission permission);
}
