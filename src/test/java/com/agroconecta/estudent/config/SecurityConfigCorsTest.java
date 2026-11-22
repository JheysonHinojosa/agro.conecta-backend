package com.agroconecta.estudent.config;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SecurityConfigCorsTest {

    @Test
    void corsAllowsOnlyConfiguredExactOrigins() {
        CorsConfiguration defaultCors = corsFor(new SecurityConfig("http://localhost:5500"));

        assertEquals(List.of("http://localhost:5500"), defaultCors.getAllowedOrigins());
        assertEquals("http://localhost:5500", defaultCors.checkOrigin("http://localhost:5500"));
        assertNull(defaultCors.checkOrigin("http://localhost:5501"));
        assertNull(defaultCors.getAllowedOriginPatterns());

        CorsConfiguration configuredCors = corsFor(
                new SecurityConfig("https://frontend.example,http://localhost:5600"));
        assertEquals(List.of("https://frontend.example", "http://localhost:5600"),
                configuredCors.getAllowedOrigins());
    }

    private CorsConfiguration corsFor(SecurityConfig config) {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/test");
        return config.corsConfigurationSource().getCorsConfiguration(request);
    }
}
