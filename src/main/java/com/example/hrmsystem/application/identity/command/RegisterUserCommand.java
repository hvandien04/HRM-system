package com.example.hrmsystem.application.identity.command;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RegisterUserCommand {
    String username;
    String password;
}
