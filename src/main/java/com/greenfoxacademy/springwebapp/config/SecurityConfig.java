package com.greenfoxacademy.springwebapp.config;

import com.greenfoxacademy.springwebapp.security.JWTRequestFilter;
import com.greenfoxacademy.springwebapp.services.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final JpaUserDetailsService myUserDetailsService;
  private final JWTRequestFilter jwtRequestFilter;

  @Autowired
  public SecurityConfig(JpaUserDetailsService myUserDetailsService, JWTRequestFilter jwtRequestFilter) {
    this.myUserDetailsService = myUserDetailsService;
    this.jwtRequestFilter = jwtRequestFilter;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable);
    http.headers((headers) -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
    http.authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/admin").hasRole("ADMIN")
            .requestMatchers("/register").permitAll()
            .requestMatchers("/api/users/**").permitAll()
            .requestMatchers("/api/**").permitAll()
            .anyRequest().authenticated())
        .exceptionHandling(exceptionHandling ->
            exceptionHandling
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint())
        )
        .userDetailsService(myUserDetailsService)
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public AccessDeniedHandler accessDeniedHandler() {
    return (request, response, accessDeniedException) -> {
      response.setStatus(HttpStatus.FORBIDDEN.value());
      response.getWriter().write("Access Denied: " + accessDeniedException.getMessage());
      response.getWriter().flush();
    };
  }

  @Bean
  public AuthenticationEntryPoint authenticationEntryPoint() {
    return (request, response, authException) -> {
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.getWriter().write("Unauthorized: " + authException.getMessage());
      response.getWriter().flush();
    };
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}