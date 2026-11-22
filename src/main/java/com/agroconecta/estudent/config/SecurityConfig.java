package com.agroconecta.estudent.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

    private final List<String> allowedOrigins;

    public SecurityConfig(@Value("${app.cors.allowed-origins:http://localhost:5500}") String configuredOrigins) {
        this.allowedOrigins = Arrays.stream(configuredOrigins.split(","))
                .map(String::trim)
                .filter(origin -> !origin.isEmpty())
                .toList();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(allowedOrigins);
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Content-Type", "X-CSRF-Token", "Accept", "Authorization", "Origin"));
        config.setExposedHeaders(List.of("Content-Type", "X-CSRF-Token"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CORS antes de los filtros de seguridad
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // Deshabilitar cache de peticiones en sesión para evitar Set-Cookie en 401
                .requestCache(cache -> cache.disable())
                // Deshabilitar CSRF nativo de Spring porque usamos CsrfValidationFilter con verificación en tiempo constante
                .csrf(csrf -> csrf.disable())
                // Gestión de sesión basada en cookies HttpOnly
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                // Reglas de autorización
                .authorizeHttpRequests(auth -> auth
                        // Permitir preflight OPTIONS libremente
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // Endpoints públicos
                        .requestMatchers("/api/auth/login", "/api/auth/me", "/actuator/health").permitAll()
                        // Gestión de usuarios: EXCLUSIVA para ADMINISTRADOR
                        .requestMatchers("/api/usuario/**").hasAnyAuthority("ADMINISTRADOR", "ROLE_ADMINISTRADOR")
                        // Todos los demás endpoints bajo /api/: acceso para ADMINISTRADOR u OPERADOR
                        .requestMatchers("/api/**").hasAnyAuthority("ADMINISTRADOR", "OPERADOR", "ROLE_ADMINISTRADOR", "ROLE_OPERADOR")
                        // Cualquier otra petición requiere autenticación
                        .anyRequest().authenticated()
                )
                // Manejo uniforme de errores 401 y 403 con formato del GlobalExceptionHandler
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(customAuthenticationEntryPoint())
                        .accessDeniedHandler(customAccessDeniedHandler())
                )
                // Filtro CSRF con comparación de tiempo constante
                .addFilterBefore(new CsrfValidationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private AuthenticationEntryPoint customAuthenticationEntryPoint() {
        return (request, response, authException) -> {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");

            String json = String.format(
                    "{\"timestamp\":\"%s\",\"status\":%d,\"error\":\"%s\",\"message\":\"%s\",\"path\":\"%s\"}",
                    OffsetDateTime.now(), HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                    "Tu sesión venció o no has iniciado sesión.", request.getRequestURI()
            );

            response.getWriter().write(json);
        };
    }

    private AccessDeniedHandler customAccessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");

            String json = String.format(
                    "{\"timestamp\":\"%s\",\"status\":%d,\"error\":\"%s\",\"message\":\"%s\",\"path\":\"%s\"}",
                    OffsetDateTime.now(), HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase(),
                    "No tienes permiso para esta operación.", request.getRequestURI()
            );

            response.getWriter().write(json);
        };
    }
}
