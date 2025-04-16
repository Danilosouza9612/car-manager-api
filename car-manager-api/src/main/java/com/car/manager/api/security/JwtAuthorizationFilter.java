package com.car.manager.api.security;

import com.car.manager.api.exception.InvalidTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_PARAM = "authorization";
    private static final int TOKEN_START = 7;

    private final AuthenticationEntryPoint entrypoint;

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    public JwtAuthorizationFilter(JwtService jwtService, UserDetailsService userDetailsService, AuthenticationEntryPoint entrypoint){
        this.entrypoint = entrypoint;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authenticationHeader = request.getHeader(AUTHORIZATION_PARAM);
        String token;
        String subject;
        UserDetails userDetails;

        if(Objects.nonNull(authenticationHeader)) {
            try {
                token = authenticationHeader.substring(TOKEN_START);
                subject = jwtService.decode(token);
                if (Objects.isNull(userDetailsService.loadUserByUsername(subject))) throw new InvalidTokenException();

                Authentication authentication = new UsernamePasswordAuthenticationToken(subject, null, null);

                SecurityContextHolder.getContext().setAuthentication(authentication);

            }catch (InvalidTokenException ex){
                entrypoint.commence(request, response, ex);
                return;
            }

        }
        filterChain.doFilter(request, response);
    }
}
