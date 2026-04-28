package com.example.hrmsystem.application.identity.service;

import com.example.hrmsystem.application.identity.command.CreatePermissionCommand;
import com.example.hrmsystem.application.identity.dto.PermissionDto;
import com.example.hrmsystem.application.identity.exception.AuthErrorCode;
import com.example.hrmsystem.application.identity.port.in.PermissionUseCase;
import com.example.hrmsystem.application.identity.port.out.PermissionPersistencePort;
import com.example.hrmsystem.domain.identity.model.PermissionModel;
import com.example.hrmsystem.interfaces.exception.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PermissionService implements PermissionUseCase {

    private final PermissionPersistencePort permissionPersistencePort;

    @Override
    public PermissionDto createPermission(CreatePermissionCommand cmd) {
        permissionPersistencePort.findByCode(cmd.getCode()).ifPresent(existing -> {
            throw new AppException(AuthErrorCode.PERMISSION_ALREADY_EXISTS, AuthErrorCode.PERMISSION_ALREADY_EXISTS.getDefaultMessage());
        });

        PermissionModel permission = PermissionModel.builder()
                .id(UUID.randomUUID())
                .code(cmd.getCode())
                .name(cmd.getName())
                .description(cmd.getDescription())
                .build();

        permissionPersistencePort.save(permission);
        return toDto(permission);
    }

    @Override
    public PermissionDto getPerm(UUID id) {
        return permissionPersistencePort.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new AppException(AuthErrorCode.PERMISSION_NOT_FOUND, AuthErrorCode.PERMISSION_NOT_FOUND.getDefaultMessage()));
    }

    @Override
    public void deleteById(UUID id) {
        getPerm(id);
        permissionPersistencePort.deleteById(id);
    }

    @Override
    public List<PermissionDto> listPermissions() {
        return permissionPersistencePort.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    private PermissionDto toDto(PermissionModel permission) {
        return PermissionDto.builder()
                .id(permission.getId())
                .code(permission.getCode())
                .name(permission.getName())
                .description(permission.getDescription())
                .build();
    }
}
