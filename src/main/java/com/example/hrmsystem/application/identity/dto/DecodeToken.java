package com.example.hrmsystem.application.identity.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DecodeToken {
    String jti;
    String sub;
    String type;
    Long exp;
}