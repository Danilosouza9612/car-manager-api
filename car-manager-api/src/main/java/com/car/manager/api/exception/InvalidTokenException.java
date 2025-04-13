package com.car.manager.api.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidTokenException extends AuthenticationException {

    public InvalidTokenException(){
        super("Unauthorized - invalid session");
    }

}
