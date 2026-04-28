package com.example.hrmsystem.domain.identity.model;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class PermissionModel {
    UUID id;
    String code;
    String name;
    String description;
}