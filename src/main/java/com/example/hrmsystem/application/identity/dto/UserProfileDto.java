package com.example.hrmsystem.application.identity.dto;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class UserProfileDto {
    UUID id;
    String username;
}