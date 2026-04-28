package com.example.hrmsystem.infrastructure.security;

import com.example.hrmsystem.application.identity.dto.DecodeToken;
import com.example.hrmsystem.application.identity.port.out.TokenPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtTokenAdapter implements TokenPort {

    private final JwtEncoder jwtEncoder;
    private final JwtProperties properties;
    private final JwtDecoder refreshJwtDecoder;


    @Override
    public String createAccessToken(Map<String, Object> claims) {
        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plusSeconds(properties.accessTokenTtlSeconds());
        claims.put("type", properties.access());
        return encode(claims, issuedAt, expiresAt);
    }

    @Override
    public String createRefreshToken(Map<String, Object> claims) {
        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plusSeconds(properties.refreshTokenTtlSeconds());
        claims.put("type", properties.refresh());
        return encode(claims, issuedAt, expiresAt);
    }

    @Override
    public DecodeToken decodeToken(String token) {
        Jwt jwt = refreshJwtDecoder.decode(token);
        assert jwt.getExpiresAt() != null;
        return DecodeToken.builder()
                .sub(jwt.getClaimAsString("sub"))
                .jti(jwt.getClaimAsString("jti"))
                .exp(jwt.getExpiresAt().getEpochSecond())
                .build();

    }

    private String encode(Map<String, Object> claims, Instant issuedAt, Instant expiresAt) {
        JwtClaimsSet.Builder builder = JwtClaimsSet.builder()
                .id(java.util.UUID.randomUUID().toString())
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .issuer(properties.issuer());

        claims.forEach(builder::claim);

        return jwtEncoder.encode(JwtEncoderParameters.from(builder.build())).getTokenValue();
    }
}

