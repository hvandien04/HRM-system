package com.example.hrmsystem.application.identity.port.in;

import com.example.hrmsystem.application.identity.command.CreatePermissionCommand;
import com.example.hrmsystem.application.identity.dto.PermissionDto;

import java.util.List;
import java.util.UUID;

public interface PermissionUseCase {
    PermissionDto createPermission(CreatePermissionCommand cmd);
    PermissionDto getPerm(UUID Id);
    void deleteById(UUID Id);
    List<PermissionDto> listPermissions();
}
