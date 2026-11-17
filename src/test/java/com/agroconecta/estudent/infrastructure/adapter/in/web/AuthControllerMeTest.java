package com.agroconecta.estudent.infrastructure.adapter.in.web;

import com.agroconecta.estudent.application.dto.response.AuthMeResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParRolEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.UsuarioEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerMeTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private AuthController authController;

    @BeforeEach
    void setUp() {
        authController = new AuthController(usuarioRepository, passwordEncoder);
    }

    @Test
    void meWithoutSessionReturnsAuthenticatedFalse() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        ResponseEntity<AuthMeResponse> response = authController.me(request);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().authenticated());
        assertNull(response.getBody().user());
        assertNull(response.getBody().csrfToken());
        assertNull(request.getSession(false));
    }

    @Test
    void meWithAnonymousTokenReturnsAuthenticatedFalse() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        SecurityContext context = new SecurityContextImpl();
        context.setAuthentication(new AnonymousAuthenticationToken(
                "key", "anonymousUser", List.of(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))));
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
        request.setSession(session);

        ResponseEntity<AuthMeResponse> response = authController.me(request);

        assertEquals(200, response.getStatusCode().value());
        assertFalse(response.getBody().authenticated());
        assertNull(response.getBody().user());
        assertNull(response.getBody().csrfToken());
    }

    @Test
    void meWithSessionReturnsAuthenticatedTrueAndCsrf() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("CSRF_TOKEN", "csrf-abc");

        SecurityContext context = new SecurityContextImpl();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(
                "administrador", null,
                List.of(new SimpleGrantedAuthority("ADMINISTRADOR"))));
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
        request.setSession(session);

        ParRolEntity rol = new ParRolEntity();
        rol.setCodigo("ADMINISTRADOR");
        UsuarioEntity user = new UsuarioEntity();
        user.setIdUsuario(1L);
        user.setUsername("administrador");
        user.setActivo(true);
        user.setRol(rol);
        when(usuarioRepository.findByUsername("administrador")).thenReturn(Optional.of(user));

        ResponseEntity<AuthMeResponse> response = authController.me(request);

        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody().authenticated());
        assertEquals("administrador", response.getBody().user().username());
        assertEquals("Administrador", response.getBody().user().rol());
        assertEquals("csrf-abc", response.getBody().csrfToken());
    }
}
