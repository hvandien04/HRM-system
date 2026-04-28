package com.example.hrmsystem.application.identity.service;

import com.example.hrmsystem.application.identity.command.CreateRoleCommand;
import com.example.hrmsystem.application.identity.command.PermissionsToRoleCommand;
import com.example.hrmsystem.application.identity.dto.RoleDto;
import com.example.hrmsystem.application.identity.exception.AuthErrorCode;
import com.example.hrmsystem.application.identity.mapper.RoleMapperApplication;
import com.example.hrmsystem.application.identity.port.in.RoleUseCase;
import com.example.hrmsystem.application.identity.port.out.PermissionPersistencePort;
import com.example.hrmsystem.application.identity.port.out.RolePersistencePort;
import com.example.hrmsystem.domain.identity.model.PermissionModel;
import com.example.hrmsystem.domain.identity.model.RoleModel;
import com.example.hrmsystem.interfaces.exception.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleService implements RoleUseCase {

    private final RolePersistencePort rolePersistencePort;
    private final PermissionPersistencePort permissionPersistencePort;
    private final RoleMapperApplication roleMapperApplication;

    @Override
    public RoleDto deletePerms(PermissionsToRoleCommand cmd) {
        RoleModel role = getRoleModelOrThrow(cmd.getRoleId());
        if (cmd.getPermissionIds() == null || cmd.getPermissionIds().isEmpty()) {
            return roleMapperApplication.toRoleDto(role);
        }

        for (UUID permissionId : cmd.getPermissionIds()) {
            role = role.removePermission(permissionId);
        }
        rolePersistencePort.save(role);
        return roleMapperApplication.toRoleDto(role);
    }

    @Override
    public RoleDto createRole(CreateRoleCommand cmd) {
        rolePersistencePort.findByCode(cmd.getCode()).ifPresent(existing -> {
            throw new AppException(AuthErrorCode.ROLE_ALREADY_EXISTS, AuthErrorCode.ROLE_ALREADY_EXISTS.getDefaultMessage());
        });

        RoleModel role = RoleModel.builder()
                .id(UUID.randomUUID())
                .code(cmd.getCode())
                .name(cmd.getName())
                .description(cmd.getDescription())
                .permissionModels(List.of())
                .build();

        rolePersistencePort.save(role);
        return roleMapperApplication.toRoleDto(role);
    }

    @Override
    public RoleDto getRole(UUID id) {
        return roleMapperApplication.toRoleDto(getRoleModelOrThrow(id));
    }

    @Override
    public List<RoleDto> listRoles() {
        return rolePersistencePort.findAll().stream()
                .map(roleMapperApplication::toRoleDto)
                .toList();
    }

    @Override
    public RoleDto assignPermissions(PermissionsToRoleCommand cmd) {
        RoleModel role = getRoleModelOrThrow(cmd.getRoleId());
        if (cmd.getPermissionIds() == null || cmd.getPermissionIds().isEmpty()) {
            return roleMapperApplication.toRoleDto(role);
        }

        List<PermissionModel> permissions = permissionPersistencePort.findByIdIn(cmd.getPermissionIds());
        if (permissions.size() != cmd.getPermissionIds().size()) {
            throw new AppException(AuthErrorCode.PERMISSION_NOT_FOUND, AuthErrorCode.PERMISSION_NOT_FOUND.getDefaultMessage());
        }

        for (PermissionModel permission : permissions) {
            role = role.addPermission(permission);
        }

        rolePersistencePort.save(role);
        return roleMapperApplication.toRoleDto(role);
    }

    @Override
    public void deleteRole(UUID id) {
        getRoleModelOrThrow(id);
        rolePersistencePort.deleteById(id);
    }

    private RoleModel getRoleModelOrThrow(UUID id) {
        return rolePersistencePort.findById(id)
                .orElseThrow(() -> new AppException(AuthErrorCode.ROLE_NOT_FOUND, AuthErrorCode.ROLE_NOT_FOUND.getDefaultMessage()));
    }
}
