package com.example.hrmsystem.application.identity.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Builder
public class RoleDto {
    UUID id;
    String code;
    String name;
    String description;
    List<PermissionDto> permissions;
}