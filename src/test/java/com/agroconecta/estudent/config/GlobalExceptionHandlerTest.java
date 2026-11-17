package com.agroconecta.estudent.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    @DisplayName("NoResourceFoundException devuelve HTTP 404 con mensaje comprensible y formato estándar")
    void noResourceFoundException_devuelve404() {
        MockHttpServletRequest req = new MockHttpServletRequest("GET", "/api/pedidos");
        NoResourceFoundException ex = new NoResourceFoundException(HttpMethod.GET, "api/pedidos", "Recurso no encontrado");

        ResponseEntity<Map<String, Object>> response = handler.notFound(ex, req);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(404, body.get("status"));
        assertEquals("Not Found", body.get("error"));
        assertEquals("El recurso solicitado no fue encontrado.", body.get("message"));
        assertEquals("/api/pedidos", body.get("path"));
        assertNotNull(body.get("timestamp"));
    }

    @Test
    @DisplayName("AuthenticationException devuelve HTTP 401 con formato estándar")
    void authenticationException_devuelve401() {
        MockHttpServletRequest req = new MockHttpServletRequest("GET", "/api/privado");
        BadCredentialsException ex = new BadCredentialsException("Credenciales inválidas");

        ResponseEntity<Map<String, Object>> response = handler.unauthorized(ex, req);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(401, body.get("status"));
        assertEquals("Unauthorized", body.get("error"));
        assertEquals("Credenciales inválidas", body.get("message"));
        assertEquals("/api/privado", body.get("path"));
    }

    @Test
    @DisplayName("AccessDeniedException devuelve HTTP 403 con formato estándar")
    void accessDeniedException_devuelve403() {
        MockHttpServletRequest req = new MockHttpServletRequest("POST", "/api/usuario");
        AccessDeniedException ex = new AccessDeniedException("Acceso denegado");

        ResponseEntity<Map<String, Object>> response = handler.forbidden(ex, req);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(403, body.get("status"));
        assertEquals("Forbidden", body.get("error"));
        assertEquals("No tienes permiso para esta operación.", body.get("message"));
        assertEquals("/api/usuario", body.get("path"));
    }
}
