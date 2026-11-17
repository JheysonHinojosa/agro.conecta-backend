package com.agroconecta.estudent.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.OffsetDateTime;
import java.util.Set;

@Slf4j
public class CsrfValidationFilter extends OncePerRequestFilter {

    private static final Set<String> SAFE_METHODS = Set.of("GET", "HEAD", "OPTIONS", "TRACE");
    private static final String CSRF_HEADER_NAME = "X-CSRF-Token";
    private static final String SESSION_CSRF_ATTR = "CSRF_TOKEN";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        String method = request.getMethod();

        // 1. Permitir métodos seguros (incluido OPTIONS para preflight CORS)
        if (SAFE_METHODS.contains(method)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Permitir endpoint de autenticación inicial (login emite el token)
        if ("/api/auth/login".equals(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Aplicar validación solo a rutas bajo /api/
        if (!path.startsWith("/api/")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 4. Validar sesión existente
        HttpSession session = request.getSession(false);
        String sessionToken = (session != null) ? (String) session.getAttribute(SESSION_CSRF_ATTR) : null;
        String clientToken = request.getHeader(CSRF_HEADER_NAME);

        boolean valid = false;
        if (sessionToken != null && clientToken != null) {
            byte[] sessionBytes = sessionToken.getBytes(StandardCharsets.UTF_8);
            byte[] clientBytes = clientToken.getBytes(StandardCharsets.UTF_8);
            valid = MessageDigest.isEqual(sessionBytes, clientBytes);
        }

        if (!valid) {
            log.warn("Rechazo CSRF: token ausente o inválido en petición {} {}", method, path);
            sendError(response, HttpStatus.FORBIDDEN, "Token CSRF inválido o ausente.", path);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void sendError(HttpServletResponse response, HttpStatus status, String message, String path)
            throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        String json = String.format(
                "{\"timestamp\":\"%s\",\"status\":%d,\"error\":\"%s\",\"message\":\"%s\",\"path\":\"%s\"}",
                OffsetDateTime.now(), status.value(), status.getReasonPhrase(), message, path
        );

        response.getWriter().write(json);
    }
}
