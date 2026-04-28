package com.example.hrmsystem.application.identity.port.out;

import com.example.hrmsystem.application.identity.dto.DecodeToken;

import java.util.Map;

public interface TokenPort {
    String createAccessToken(Map<String, Object> claims);
    String createRefreshToken(Map<String, Object> claims);
    DecodeToken decodeToken(String token);

}