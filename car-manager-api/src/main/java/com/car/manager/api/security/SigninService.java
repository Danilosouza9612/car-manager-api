package com.car.manager.api.security;

import com.car.manager.api.dto.login.SigninRequestDTO;
import com.car.manager.api.dto.login.SigninResponseDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SigninService {
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public SigninService(JwtService jwtService, AuthenticationManager authenticationManager){
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public SigninResponseDTO signin(SigninRequestDTO requestDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDTO.login(), requestDTO.password())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return new SigninResponseDTO(jwtService.encode(userDetails.getUsername()));
    }
}
