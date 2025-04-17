package com.car.manager.api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.car.manager.api.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class JwtkJwtService implements JwtService{
    @Value("${com.car.manager.api.jwt.key}")
    private String key;

    private Algorithm algorithm;

    private static final String ISSUER = "car-manager-api";

    @Override
    public String encode(String subject) {
        Instant createdAt = creationDate();
        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(subject)
                .withIssuedAt(createdAt)
                .withExpiresAt(expirationDate(createdAt))
                .sign(getAlgorithm());
    }

    @Override
    public String decode(String token) {
        try {
            return JWT.require(getAlgorithm())
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException ex){
            throw new InvalidTokenException();
        }
    }

    private Algorithm getAlgorithm(){
        if(algorithm==null) algorithm=Algorithm.HMAC256(key);

        return algorithm;
    }

    private Instant creationDate(){
        return Instant.now();
    }

    private Instant expirationDate(Instant creationDate){
        return Instant.from(creationDate).plus(4, ChronoUnit.HOURS);
    }
}
