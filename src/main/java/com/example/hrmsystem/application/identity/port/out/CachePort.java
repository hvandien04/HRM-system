package com.example.hrmsystem.application.identity.port.out;

public interface CachePort {
    boolean isCacheExist(String key);
    void deleteCache(String key);
    void createCache(String key, String value, long expiration);
    void setWithSeconds(String key, String value, long expiration);
    int getIntegerValue(String key);
    void increment(String key, long amount, long ttlMinutes);
}