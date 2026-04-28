package com.example.hrmsystem.application.identity.port.in;

import com.example.hrmsystem.application.identity.command.CreateRoleCommand;
import com.example.hrmsystem.application.identity.command.PermissionsToRoleCommand;
import com.example.hrmsystem.application.identity.dto.RoleDto;

import java.util.List;
import java.util.UUID;

public interface RoleUseCase {
    RoleDto deletePerms(PermissionsToRoleCommand cmd);
    RoleDto createRole(CreateRoleCommand cmd);
    RoleDto getRole(UUID Id);
    List<RoleDto> listRoles();
    RoleDto assignPermissions(PermissionsToRoleCommand cmd);
    void deleteRole(UUID Id);
}
