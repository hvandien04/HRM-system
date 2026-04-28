package com.example.hrmsystem.application.identity.command;

import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Builder
public class PermissionsToRoleCommand {
    UUID roleId;
    List<UUID> permissionIds;
}