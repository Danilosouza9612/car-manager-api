package com.car.manager.api.security;

import com.car.manager.api.dto.entrypoint.ApiEntryPointResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class ApiEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper mapper;

    public ApiEntryPoint(ObjectMapper mapper){
        this.mapper = mapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        if(authException instanceof InternalAuthenticationServiceException || authException instanceof BadCredentialsException)
            response.getWriter().write(
                    mapper.writeValueAsString(new ApiEntryPointResponse("Invalid login or password"))
            );
        else
            response.getWriter().write(
                    mapper.writeValueAsString(new ApiEntryPointResponse(authException.getLocalizedMessage()))
            );
    }
}
