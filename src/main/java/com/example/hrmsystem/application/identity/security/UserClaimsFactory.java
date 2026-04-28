package com.example.hrmsystem.application.identity.security;

import com.example.hrmsystem.domain.identity.model.UserModel;

import java.util.Map;

public interface UserClaimsFactory {
    Map<String, Object> createClaim(UserModel user);
}
