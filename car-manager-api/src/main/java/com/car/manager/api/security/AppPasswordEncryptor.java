package com.car.manager.api.security;

import com.car.manager.core.security.PasswordEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AppPasswordEncryptor implements PasswordEncryptor {

    private final PasswordEncoder passwordEncoder;

    public AppPasswordEncryptor(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encrypt(String password) {
        return passwordEncoder.encode(password);
    }
}
