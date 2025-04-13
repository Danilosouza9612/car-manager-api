package com.car.manager.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JwtAuthenticationFilterTest {
    private HttpServletRequest request;

    private HttpServletResponse response;

    private JwtService jwtService;

    private AuthenticationManager authenticationManager;

    private JwtAuthenticationFilter subject;

    private AuthenticationEntryPoint entryPoint;

    private FilterChain filterChain;

    private ObjectMapper objectMapper;

    private String username;

    private String password;

    @BeforeEach
    public void setup() throws IOException {
        authenticationManager = mock(AuthenticationManager.class);
        entryPoint = mock(AuthenticationEntryPoint.class);
        filterChain = mock(FilterChain.class);
        objectMapper = new ObjectMapper();
        jwtService = new JwtkJwtService();

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        username = "root";
        password = "123456";

        subject = new JwtAuthenticationFilter(entryPoint, authenticationManager, objectMapper, jwtService);
    }

    @Test
    public void doFilter_whenRequestPathIsNotToSignin() throws ServletException, IOException {
        subject.doFilterInternal(request, response, filterChain);

        verify(authenticationManager, never()).authenticate(any(Authentication.class));
        verify(entryPoint, never()).commence(any(HttpServletRequest.class), any(HttpServletResponse.class), any(AuthenticationException.class));
        verify(filterChain).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));
    }

    @Test
    public void generateToken_whenRequestPathIsSigninAndLoginIsValid() throws IOException, ServletException {
        mockSigninRequestPath();

        String requestBody = buildRequestBody(username, password);
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(username);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        mockResponseWriter();
        mockBufferedReader(request, requestBody);
        when(authenticationManager.authenticate(eq(new UsernamePasswordAuthenticationToken(username, password)))).thenReturn(authentication);

        subject.doFilterInternal(request, response, filterChain);

        verify(filterChain, never()).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));
    }

    @Test
    public void callEntryPoint_whenRequestPathIsSigninAndLoginPasswordIsInvalid() throws ServletException, IOException {
        whenRequestPathIsSigninAndLoginIsInvalid(BadCredentialsException.class);
    }

    @Test
    public void callEntryPoint_whenRequestPathIsSigninAndLoginUsernameIsInvalid() throws ServletException, IOException {
        whenRequestPathIsSigninAndLoginIsInvalid(InternalAuthenticationServiceException.class);
    }

    private void whenRequestPathIsSigninAndLoginIsInvalid(Class<? extends AuthenticationException> clazz) throws ServletException, IOException{
        mockSigninRequestPath();

        String requestBody = buildRequestBody(username, password);
        when(authenticationManager.authenticate(eq(new UsernamePasswordAuthenticationToken(username, password)))).thenThrow(clazz);
        mockBufferedReader(request, requestBody);

        subject.doFilterInternal(request, response, filterChain);

        verify(filterChain, never()).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));
        verify(entryPoint).commence(any(HttpServletRequest.class), any(HttpServletResponse.class), any(clazz));
    }

    private String buildRequestBody(String username, String password){
        return "{\"login\": \""+username+"\", \"password\": \""+password+"\"}";
    }

    private void mockSigninRequestPath(){
        when(request.getServletPath()).thenReturn("/api/signin");
        when(request.getMethod()).thenReturn("POST");
    }

    private void mockBufferedReader(HttpServletRequest request, String requestBody) throws IOException {
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(requestBody)));
    }

    private void mockResponseWriter() throws IOException {
        when(response.getWriter()).thenReturn(mock(PrintWriter.class));
    }
}
