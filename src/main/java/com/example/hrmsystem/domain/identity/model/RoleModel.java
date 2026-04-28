package com.example.hrmsystem.domain.identity.model;

import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class RoleModel {
    UUID id;
    String code;
    String name;
    String description;
    List<PermissionModel> permissionModels;

    public RoleModel addPermission(PermissionModel permissionModel) {
        List<PermissionModel> current = permissionModels != null ? permissionModels : List.of();

        boolean existed = current.stream().anyMatch(p -> p.getCode().equals(permissionModel.getCode()));
        if(existed)
            return this;

        List<PermissionModel> updated = new ArrayList<>(current);
        updated.add(permissionModel);
        return this.toBuilder()
                .permissionModels(List.copyOf(updated))
                .build();

    }

    public RoleModel removePermission(UUID Id) {
        List<PermissionModel> current = permissionModels != null ? permissionModels : List.of();

        List<PermissionModel> updated = current.stream()
                .filter(p -> !p.getId().equals(Id))
                .toList();
        return this.toBuilder()
                .permissionModels(List.copyOf(updated))
                .build();

    }

}