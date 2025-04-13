package com.car.manager.api.security;

public interface JwtService {
    String encode(String subject);
    String decode(String token);
}
