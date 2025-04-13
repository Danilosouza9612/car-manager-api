package com.car.manager.api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.car.manager.api.exception.InvalidTokenException;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class JwtkJwtService implements JwtService{
    private static final Algorithm algorithm = Algorithm.HMAC256("eff7e8c32bace7d2b117f5fb04b211b51d2dbbe3ea283f9d7509a54339297cfb133baca25628df9dce1f95b68d8d26e6858b67ca1b280a83ff8ceb1d63d58c92276a5322b08e7c9102606d66427eaa513bf715f0bcfa2b6af84f4a06f32f694dd3e468d4b8475d5a555e26ef3676528cc7a7d39148d133cb68c964828de964fbc1ee9441ff0273fdf5bcf4668ae043b6c23e139679eaa0f2105a8d4cdb39bc50971d4fc32c85fda629f98898eed2ab23906bb4c3a28963edc21c964b54b7012e8b35311298f11f2450a90cd3bf654b056f9304ad470d1d5dda889f529566c51a5daea12b24356fa840148e07984918144bfe80fe060ce0c45f9d9f9e0fe2a5ef");
    private static final String ISSUER = "car-manager-api";

    @Override
    public String encode(String subject) {
        Instant createdAt = creationDate();
        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(subject)
                .withIssuedAt(createdAt)
                .withExpiresAt(expirationDate(createdAt))
                .sign(algorithm);
    }

    @Override
    public String decode(String token) {
        try {
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException ex){
            throw new InvalidTokenException();
        }
    }

    private Instant creationDate(){
        return Instant.now();
    }

    private Instant expirationDate(Instant creationDate){
        return Instant.from(creationDate).plus(4, ChronoUnit.HOURS);
    }
}
