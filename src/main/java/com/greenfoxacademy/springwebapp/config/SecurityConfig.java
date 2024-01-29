package com.greenfoxacademy.springwebapp.config;

import com.greenfoxacademy.springwebapp.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Autowired
  public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable);
    http.authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.POST, "/api/news").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/api/news/{articleId}").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/news/{articleId}").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/products/{productId}").hasRole("ADMIN")
            .requestMatchers(HttpMethod.POST, "/api/products").hasRole("ADMIN")
            .requestMatchers(HttpMethod.POST, "/api/product-types").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PATCH, "/api/users").authenticated()
            .requestMatchers(HttpMethod.PATCH, "/api/products/{productId}").hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/api/news").permitAll()
            .requestMatchers("/",
                "/images/**",
                "/css/**",
                "/js/**",
                "/register",
                "/login",
                "/api/users/**").permitAll()
            .anyRequest().authenticated())
        .exceptionHandling(exceptionHandling ->
            exceptionHandling
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint())
        )
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
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