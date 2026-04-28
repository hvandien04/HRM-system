package com.example.hrmsystem.infrastructure.web;

import com.example.hrmsystem.application.identity.dto.AuthTokensDto;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthCookieWriter {
    void writeTokens(HttpServletResponse response, AuthTokensDto tokens);
    void clearTokens(HttpServletResponse response);
}