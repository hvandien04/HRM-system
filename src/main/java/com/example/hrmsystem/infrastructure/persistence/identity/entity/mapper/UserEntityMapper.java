package com.example.hrmsystem.infrastructure.persistence.identity.entity.mapper;

import com.example.hrmsystem.domain.identity.model.UserModel;
import com.example.hrmsystem.domain.identity.value.PasswordHash;
import com.example.hrmsystem.infrastructure.persistence.identity.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {
    User toEntity(UserModel userModel);
    UserModel toDto(User user);

    default String passwordHashToString(PasswordHash value) {
        return value == null ? null : value.getPasswordHash();
    }

    default PasswordHash stringToPasswordHash(String value) {
        return value == null ? null : new PasswordHash(value);
    }
}
