package com.car.manager.api.security;

import com.car.manager.api.dto.login.SigninRequestDTO;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SigninServiceTest {
    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    private SigninService subject;

    private String username;

    private String password;

    @BeforeEach
    public void setup() throws IOException {
        username = "root";
        password = "123456";

        subject = new SigninService(jwtService, authenticationManager);

    }

    @Test
    public void generateToken_whenRequestPathIsSigninAndLoginIsValid() throws IOException, ServletException {
        SigninRequestDTO requestDTO = new SigninRequestDTO(username, password);
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(username);
        when(jwtService.encode(eq(requestDTO.login()))).thenReturn(requestDTO.login());
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(authenticationManager.authenticate(eq(new UsernamePasswordAuthenticationToken(username, password)))).thenReturn(authentication);

        subject.signin(requestDTO);
    }

    @Test
    public void throwsException_whenPasswordInvalid() throws ServletException, IOException {
        whenRequestPathIsSigninAndLoginIsInvalid(BadCredentialsException.class);
    }

    @Test
    public void throwsException_whenLoginNotFound() throws ServletException, IOException {
        whenRequestPathIsSigninAndLoginIsInvalid(InternalAuthenticationServiceException.class);
    }

    private void whenRequestPathIsSigninAndLoginIsInvalid(Class<? extends AuthenticationException> clazz) throws ServletException, IOException{

        SigninRequestDTO requestDTO = new SigninRequestDTO(username, password);
        when(authenticationManager.authenticate(eq(new UsernamePasswordAuthenticationToken(username, password)))).thenThrow(clazz);


        Assertions.assertThrows(clazz, () -> subject.signin(requestDTO));

        verify(jwtService, never()).decode(any(String.class));
    }

}
