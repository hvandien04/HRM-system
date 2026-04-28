package com.example.hrmsystem.application.identity.port.in;

import com.example.hrmsystem.application.identity.command.RegisterUserCommand;
import com.example.hrmsystem.application.identity.dto.UserProfileDto;

public interface RegisterUserUseCase {
    UserProfileDto register(RegisterUserCommand command);
}
