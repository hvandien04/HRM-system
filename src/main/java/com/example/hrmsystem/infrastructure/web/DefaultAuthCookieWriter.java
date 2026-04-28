package com.example.hrmsystem.infrastructure.web;

import com.example.hrmsystem.application.identity.dto.AuthTokensDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class DefaultAuthCookieWriter implements AuthCookieWriter {


    @Override
    public void writeTokens(HttpServletResponse response, AuthTokensDto tokens) {
        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie(tokens.getAccessToken()).toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie(tokens.getRefreshToken()).toString());
    }

    @Override
    public void clearTokens(HttpServletResponse response) {
        response.addHeader(HttpHeaders.SET_COOKIE, expireCookie(CookieProperties.ACCESS_TOKEN, "/").toString());
        response.addHeader(HttpHeaders.SET_COOKIE, expireCookie(CookieProperties.REFRESH_TOKEN, "/").toString());
    }

    private ResponseCookie accessCookie(String token) {
        return base("access_token", token)
                .path("/")
                .maxAge(CookieProperties.DEFAULT_ACCESS_TOKEN_TTL_SECONDS)
                .build();
    }

    private ResponseCookie refreshCookie(String token) {
        return base("refresh_token", token)
                .path("/")
                .maxAge(CookieProperties.DEFAULT_REFRESH_TOKEN_TTL_SECONDS)
                .build();
    }

    private ResponseCookie expireCookie(String name, String path) {
        return base(name, "")
                .path(path)
                .maxAge(0)
                .build();
    }

    private ResponseCookie.ResponseCookieBuilder base(String name, String value) {
        return ResponseCookie.from(name, value)
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax");
    }
}