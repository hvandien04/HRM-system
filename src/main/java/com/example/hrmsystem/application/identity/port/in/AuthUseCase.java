package com.example.hrmsystem.application.identity.port.in;

import com.example.hrmsystem.application.identity.command.LoginCommand;
import com.example.hrmsystem.application.identity.command.TokenCommand;
import com.example.hrmsystem.application.identity.dto.AuthTokensDto;

public interface AuthUseCase {
    AuthTokensDto login(LoginCommand loginCommand);
    AuthTokensDto refreshToken(TokenCommand token);
    void logout(TokenCommand token);
}
