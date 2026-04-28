package com.example.hrmsystem.application.identity.security;

import com.example.hrmsystem.domain.identity.model.PermissionModel;
import com.example.hrmsystem.domain.identity.model.RoleModel;
import com.example.hrmsystem.domain.identity.model.UserModel;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;

@Component
public class DefaultUserClaimsFactory implements UserClaimsFactory {

    @Override
    public Map<String, Object> createClaim(UserModel user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", user.getId().toString());
        claims.put("username", user.getUsername());
        claims.put("roles", user.getRoles() == null ? java.util.List.of() : user.getRoles().stream()
                .filter(Objects::nonNull)
                .map(RoleModel::getCode)
                .filter(Objects::nonNull)
                .distinct()
                .toList());
        claims.put("permissions", user.getRoles() == null ? java.util.List.of() : user.getRoles().stream()
                .filter(Objects::nonNull)
                .flatMap(role -> role.getPermissionModels() == null ? java.util.stream.Stream.empty() : role.getPermissionModels().stream())
                .filter(Objects::nonNull)
                .map(PermissionModel::getCode)
                .filter(Objects::nonNull)
                .collect(java.util.stream.Collectors.toCollection(LinkedHashSet::new))
                .stream().toList());
        return claims;
    }
}
