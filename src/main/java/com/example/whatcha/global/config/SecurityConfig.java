package com.example.whatcha.global.config;

import com.example.whatcha.domain.user.dao.LogoutAccessTokenRedisRepository;
import com.example.whatcha.domain.user.service.UserRedisService;
import com.example.whatcha.global.jwt.JwtTokenProvider;
import com.example.whatcha.global.security.CustomAuthenticationProvider;
import com.example.whatcha.global.security.filter.CustomAuthenticationFilter;
import com.example.whatcha.global.security.handler.CustomAccessDeniedHandler;
import com.example.whatcha.global.security.handler.CustomAuthenticationEntryPoint;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRedisService userRedisService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;
    private final LogoutAccessTokenRedisRepository logoutAccessTokenRedisRepository;
    private final CustomAuthenticationProvider customAuthenticationProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(customAuthenticationProvider)
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/ws/**").permitAll()
                .anyRequest().permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new CustomAccessDeniedHandler(objectMapper))
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint(objectMapper))
                .and()
                .addFilterBefore(
                        new CustomAuthenticationFilter(userRedisService, jwtTokenProvider, objectMapper, logoutAccessTokenRedisRepository),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
