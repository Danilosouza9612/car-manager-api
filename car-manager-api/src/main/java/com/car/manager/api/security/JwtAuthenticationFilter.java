package com.car.manager.api.security;

import com.car.manager.api.dto.login.SigninRequestDTO;
import com.car.manager.api.dto.login.SigninResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final AuthenticationEntryPoint entryPoint;
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;
    private final JwtService jwtService;
    private final RequestMatcher requestMatcher = new AntPathRequestMatcher(SecurityConfig.SIGNIN_URL, HttpMethod.POST.name());

    public JwtAuthenticationFilter(
            AuthenticationEntryPoint entryPoint,
            AuthenticationManager authenticationManager,
            ObjectMapper objectMapper,
            JwtService jwtService
    ){
        this.entryPoint = entryPoint;
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        if(requestMatcher.matches(request)) {
            SigninRequestDTO signinRequestDTO = objectMapper.readValue(getBody(request), SigninRequestDTO.class);
            String token;

            try {
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequestDTO.login(), signinRequestDTO.password()));
                SigninResponseDTO signinResponseDTO = new SigninResponseDTO(jwtService.encode(((UserDetails) authentication.getPrincipal()).getUsername()));
                writeResponse(response, new SigninResponseDTO(jwtService.encode(((UserDetails) authentication.getPrincipal()).getUsername())));
            } catch (AuthenticationException ex) {
                entryPoint.commence(request, response, ex);
            }
        }else{
            filterChain.doFilter(request, response);
        }
    }

    private String getBody(HttpServletRequest request) throws IOException {
        return request.getReader().lines().collect(Collectors.joining());
    }

    private void writeResponse(HttpServletResponse response, SigninResponseDTO signinResponseDTO) throws IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Request-Headers", "*");
        response.addHeader("Access-Control-Request-Method", "*");

        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(signinResponseDTO));
    }
}
