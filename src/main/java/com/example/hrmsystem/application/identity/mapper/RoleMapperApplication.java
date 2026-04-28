package com.example.hrmsystem.application.identity.mapper;

import com.example.hrmsystem.application.identity.dto.PermissionDto;
import com.example.hrmsystem.application.identity.dto.RoleDto;
import com.example.hrmsystem.domain.identity.model.PermissionModel;
import com.example.hrmsystem.domain.identity.model.RoleModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapperApplication {
    RoleDto toRoleDto(RoleModel role);
    RoleModel toRole(RoleDto roleDto);

    PermissionDto toPermDto(PermissionModel permission);
    PermissionModel toPerm(PermissionDto permissionDto);
}
