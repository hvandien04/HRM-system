package com.example.hrmsystem.application.identity.command;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CreateRoleCommand {
    String code;
    String name;
    String description;
}