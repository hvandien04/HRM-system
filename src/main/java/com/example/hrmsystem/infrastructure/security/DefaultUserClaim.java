package com.example.hrmsystem.infrastructure.security;

import com.example.hrmsystem.application.identity.security.UserClaimsFactory;
import com.example.hrmsystem.domain.identity.model.PermissionModel;
import com.example.hrmsystem.domain.identity.model.RoleModel;
import com.example.hrmsystem.domain.identity.model.UserModel;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DefaultUserClaim implements UserClaimsFactory {
    @Override
    public Map<String, Object> createClaim(UserModel user) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("sub", user.getId());
        claims.put("username", user.getUsername());


        List<RoleModel> roles = user.getRoles();
        if (roles == null || roles.isEmpty()) {
            claims.put("roles", List.of());
            claims.put("permissions", List.of());
            return claims;
        }
        List<String> roleCodes = roles.stream()
                .map(RoleModel::getCode)
                .map(code -> "ROLE_" + code)
                .distinct()
                .toList();

        claims.put("roles", roleCodes);

        List<String> permissions = roles.stream()
                .filter(r -> r.getPermissionModels() != null)
                .flatMap(r -> r.getPermissionModels().stream())
                .map(PermissionModel::getCode)
                .distinct()
                .toList();
        claims.put("permissions", permissions);
        return claims;
    }
}
