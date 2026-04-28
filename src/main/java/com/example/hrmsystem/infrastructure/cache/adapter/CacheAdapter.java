package com.example.hrmsystem.infrastructure.cache.adapter;

import com.example.hrmsystem.application.identity.port.out.CachePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class CacheAdapter implements CachePort {

    private final StringRedisTemplate redis;

    @Override
    public boolean isCacheExist(String key) {
        return redis.hasKey(key);
    }

    @Override
    public void deleteCache(String key) {
        redis.delete(key);
    }

    @Override
    public void createCache(String key, String value, long expiration) {
        redis.opsForValue().set(key, value, expiration, TimeUnit.MINUTES);
    }

    @Override
    public void setWithSeconds(String key, String value, long expiration) {
        redis.opsForValue().set(key, value, expiration, TimeUnit.SECONDS);
    }

    @Override
    public int getIntegerValue(String key) {
        String value = redis.opsForValue().get(key);
        try {
            return (value == null) ? 0 : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public void increment(String key, long amount, long ttlMinutes) {
        Long currentVal = redis.opsForValue().increment(key, amount);
        if (currentVal != null && currentVal == amount) {
            redis.expire(key, ttlMinutes, TimeUnit.MINUTES);
        }
    }
}