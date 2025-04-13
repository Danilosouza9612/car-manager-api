package com.car.manager.api.security;

import com.car.manager.api.exception.InvalidTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JwtAuthorizationFilterTest {
    private static final String AUTHORIZATION_PARAM = "authorization";

    private static final String TOKEN = "abcdefghijklmnopq";

    private static final String TOKEN_PREFIX = "Bearer ";

    private static final String SUBJECT = "root";

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private AuthenticationEntryPoint entryPoint;

    @Mock
    private FilterChain filterChain;

    private JwtAuthorizationFilter subject;

    @BeforeEach
    public void setup(){
        this.subject = new JwtAuthorizationFilter(jwtService, userDetailsService, entryPoint);
    }

    @Test
    public void doFilter_whenTokenIsNotPresent() throws ServletException, IOException {
        subject.doFilterInternal(request, response, filterChain);

        verify(userDetailsService, never()).loadUserByUsername(anyString());
        verify(entryPoint, never()).commence(any(HttpServletRequest.class), any(HttpServletResponse.class), any(AuthenticationException.class));
        verify(filterChain).doFilter(eq(request), eq(response));
    }

    @Test
    public void createSecurityHolderInstance_whenTokenIsPresentAndValid() throws ServletException, IOException {
        when(request.getHeader(eq(AUTHORIZATION_PARAM))).thenReturn(TOKEN_PREFIX + TOKEN);
        when(jwtService.decode(eq(TOKEN))).thenReturn(SUBJECT);
        when(userDetailsService.loadUserByUsername(eq(SUBJECT))).thenReturn(mock(UserDetails.class));

        subject.doFilterInternal(request, response, filterChain);

        verify(userDetailsService).loadUserByUsername(anyString());
        verify(entryPoint, never()).commence(any(HttpServletRequest.class), any(HttpServletResponse.class), any(AuthenticationException.class));
        verify(filterChain).doFilter(eq(request), eq(response));
    }

    @Test
    public void callEntryPoint_whenTokenIsPresentAndSubjectNotFound() throws  ServletException, IOException{
        when(request.getHeader(eq(AUTHORIZATION_PARAM))).thenReturn(TOKEN_PREFIX + TOKEN);
        when(jwtService.decode(eq(TOKEN))).thenReturn(SUBJECT);

        subject.doFilterInternal(request, response, filterChain);

        verify(userDetailsService).loadUserByUsername(anyString());
        verify(entryPoint).commence(eq(request), eq(response), any(InvalidTokenException.class));
        verify(filterChain, never()).doFilter(eq(request), eq(response));
    }

    @Test
    public void callEntryPoint_whenTokenIsInvalid() throws  ServletException, IOException{
        when(request.getHeader(eq(AUTHORIZATION_PARAM))).thenReturn(TOKEN_PREFIX + TOKEN);
        when(jwtService.decode(eq(TOKEN))).thenThrow(InvalidTokenException.class);

        subject.doFilterInternal(request, response, filterChain);

        verify(userDetailsService, never()).loadUserByUsername(anyString());
        verify(entryPoint).commence(eq(request), eq(response), any(InvalidTokenException.class));
        verify(filterChain, never()).doFilter(eq(request), eq(response));
    }

}
