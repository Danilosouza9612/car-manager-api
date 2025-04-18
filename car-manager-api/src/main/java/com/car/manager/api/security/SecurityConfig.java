package com.car.manager.api.security;

import com.car.manager.core.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

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
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        (auth) -> auth.requestMatchers("/error").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/signin").permitAll()
                                .requestMatchers(HttpMethod.POST,"/api/users").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/users/{id}").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/api/users/{id}").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/users/{id}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/users").permitAll()
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/users/{id}/upload_photo").permitAll()
                                .requestMatchers("/api-docs/**").permitAll()
                                .requestMatchers("/swagger-ui/**").permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthorizationFilter(jwtService, appUserDetailsService, apiEntryPoint), BasicAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(AbstractHttpConfigurer::disable)
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

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService,
            UserService userService
    ) {
        AppDaoAuthenticationProvider appDaoAuthenticationProvider = new AppDaoAuthenticationProvider();
        appDaoAuthenticationProvider.setUserDetailsService(userDetailsService);
        appDaoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        appDaoAuthenticationProvider.setUserService(userService);

        return appDaoAuthenticationProvider;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source =
                new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
