package com.digi.ecommerce.digi_shop.infra.security;

import com.digi.ecommerce.digi_shop.common.Roles;
import com.digi.ecommerce.digi_shop.infra.security.exception.handler.ApplicationAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTAuthenticationFilter jwtAuthenticationFilter;

    private final ApplicationAuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;


    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        final var authWhitelist = new String[]{
                "/api-docs/**", "/auth/refresh-token",
                "/auth/signin", "/auth/signup", "/swagger-ui/**",
                "/doc/**", "/index.html", "/error/**"};


        http.
                csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(
                        customizer ->
                                customizer
                                        .accessDeniedHandler(accessDeniedHandler)
                                        .authenticationEntryPoint(authenticationEntryPoint))
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(
                        mather ->
                                mather
                                        .requestMatchers(authWhitelist)
                                        .permitAll()
                )
                .authorizeHttpRequests(
                        matcher ->
                                matcher.requestMatchers("/users/all").hasAuthority(Roles.ADMIN.name())
                                        .requestMatchers(HttpMethod.POST, "/products").hasAuthority(Roles.ADMIN.name())
                                        .requestMatchers(HttpMethod.PATCH, "/products").hasAuthority(Roles.ADMIN.name())
                                        .requestMatchers(HttpMethod.DELETE, "/products").hasAuthority(Roles.ADMIN.name())
                )
                .authorizeHttpRequests(
                        mather ->
                                mather.anyRequest().authenticated()
                );

        return http.build();
    }
}
