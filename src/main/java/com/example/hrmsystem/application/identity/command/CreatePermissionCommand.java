package com.example.hrmsystem.application.identity.command;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreatePermissionCommand {
    String code;
    String name;
    String description;
}