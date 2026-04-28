package com.example.hrmsystem.application.identity.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class AuthTokensDto {
    String accessToken;
    String refreshToken;
}