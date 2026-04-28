package com.example.hrmsystem.application.identity.service;

import com.example.hrmsystem.application.identity.command.LoginCommand;
import com.example.hrmsystem.application.identity.command.TokenCommand;
import com.example.hrmsystem.application.identity.dto.AuthTokensDto;
import com.example.hrmsystem.application.identity.dto.DecodeToken;
import com.example.hrmsystem.application.identity.port.in.AuthUseCase;
import com.example.hrmsystem.application.identity.port.out.CachePort;
import com.example.hrmsystem.application.identity.port.out.PasswordHasherPort;
import com.example.hrmsystem.application.identity.port.out.TokenPort;
import com.example.hrmsystem.application.identity.port.out.UserPersistencePort;
import com.example.hrmsystem.application.identity.security.UserClaimsFactory;
import com.example.hrmsystem.domain.identity.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {

    private final UserPersistencePort userPersistencePort;
    private final PasswordHasherPort passwordHasherPort;
    private final TokenPort tokenPort;
    private final CachePort cachePort;


    private final UserClaimsFactory userClaimsFactory;


    private static final String REVOKE_PREFIX = "revoked:";
    private static final String LOGIN_FAIL_PREFIX = "login_fail:";
    private static final String LOCK_LOGIN_PREFIX = "lock_login:";
    private static final String OTP_PREFIX = "auth:forget_password:" ;
    private static final String OTP_COOL_DOWN_PREFIX = "auth:otp:cooldown:";
    private static final String OTP_LIMIT_PREFIX = "auth:otp:limit:";

    public void handleLoginFail(String email) {
        String key = LOGIN_FAIL_PREFIX + email;
        cachePort.increment(key, 1, 10);

        int failCount = cachePort.getIntegerValue(key);
        if (failCount >= 5) {
            cachePort.createCache(LOCK_LOGIN_PREFIX + email, "locked", 10);
            cachePort.deleteCache(key);
        }
    }

    @Override
    public AuthTokensDto login(LoginCommand loginCommand) {

        if(cachePort.isCacheExist(LOCK_LOGIN_PREFIX+loginCommand.getUsername())) {
            throw new AppException(ErrorCode.LOGIN_BLOCKED);
        }

        UserModel user = userPersistencePort.findByUsername(loginCommand.getUsername())
                .filter(u -> u.getPasswordHash() != null)
                .filter(u -> passwordHasherPort.matches(loginCommand.getPassword(), u.getPasswordHash().getPasswordHash()))
                .orElseThrow(() -> {
                    this.handleLoginFail(loginCommand.getUsername());
                    return new AppException(ErrorCode.AUTHENTICATION_FAIL);
                });

        user = user.login(Instant.now());

        cachePort.deleteCache(LOGIN_FAIL_PREFIX + loginCommand.getUsername());

        return AuthTokensDto.builder()
                .accessToken(tokenPort.createAccessToken(userClaimsFactory.createClaim(user)))
                .refreshToken(tokenPort.createRefreshToken(userClaimsFactory.createClaim(user)))
                .build();
    }




    @Override
    public AuthTokensDto refreshToken(TokenCommand token) {
        DecodeToken decodeDeToken = tokenPort.decodeToken(token.getRefreshToken());
        long ttl = decodeDeToken.getExp() - Instant.now().getEpochSecond();
        if (ttl > 0) {
            cachePort.setWithSeconds(REVOKE_PREFIX + decodeDeToken.getJti(), "", ttl);
        }
        UserModel user = userPersistencePort.findById(UUID.fromString(decodeDeToken.getSub()));
        return AuthTokensDto.builder()
                .accessToken(tokenPort.createAccessToken(userClaimsFactory.createClaim(user)))
                .refreshToken(tokenPort.createRefreshToken(userClaimsFactory.createClaim(user)))
                .build();
    }

    @Override
    public void logout(TokenCommand token) {
        DecodeToken decodeToken = tokenPort.decodeToken(token.getRefreshToken());
        long ttl = decodeToken.getExp() - Instant.now().getEpochSecond();
        if (ttl > 0) {
            cachePort.setWithSeconds(REVOKE_PREFIX + decodeToken.getJti(), "", ttl);
        }
    }
}