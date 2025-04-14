package com.car.manager.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

    public static final String SIGNIN_URL = "/api/signin";
    @Bean
    public DefaultSecurityFilterChain configure(
            HttpSecurity httpSecurity,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            AppUserDetailsService appUserDetailsService,
            ObjectMapper mapper
    ) throws Exception {
        ApiEntryPoint apiEntryPoint = new ApiEntryPoint(mapper);

        return httpSecurity
                .authorizeHttpRequests(
                        (auth) -> auth.requestMatchers("/error").permitAll()
                                .requestMatchers(HttpMethod.POST,"/api/users").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/users/{id}").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/api/users/{id}").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/users/{id}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/users").permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterAt(new JwtAuthenticationFilter(apiEntryPoint, authenticationManager, mapper, jwtService), BasicAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthorizationFilter(jwtService, appUserDetailsService, apiEntryPoint), BasicAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
