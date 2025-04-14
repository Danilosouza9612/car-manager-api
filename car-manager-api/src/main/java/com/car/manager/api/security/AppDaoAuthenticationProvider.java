package com.car.manager.api.security;

import com.car.manager.core.service.UserService;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class AppDaoAuthenticationProvider extends DaoAuthenticationProvider {
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication successAuthentication = super.authenticate(authentication);
        String login = ((String) authentication.getPrincipal());
        userService.updateLogin(login);
        return successAuthentication;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
