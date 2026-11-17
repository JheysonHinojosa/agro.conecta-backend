package com.agroconecta.estudent.infrastructure.adapter.in.web;

import com.agroconecta.estudent.application.dto.request.AuthLoginRequest;
import com.agroconecta.estudent.application.dto.response.AuthLoginResponse;
import com.agroconecta.estudent.application.dto.response.AuthMeResponse;
import com.agroconecta.estudent.application.dto.response.AuthUserResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.UsuarioEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private static final String ERROR_CREDENCIALES = "Usuario o contraseña incorrectos.";

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<AuthLoginResponse> login(@Valid @RequestBody AuthLoginRequest request,
                                                  HttpServletRequest httpRequest) {
        log.info("Intento de inicio de sesión para el usuario: {}", request.username());

        Optional<UsuarioEntity> optUser = usuarioRepository.findByUsername(request.username().trim());
        if (optUser.isEmpty()) {
            log.warn("Login fallido: usuario no encontrado");
            throw new BadCredentialsException(ERROR_CREDENCIALES);
        }

        UsuarioEntity user = optUser.get();

        if (!user.isActivo()) {
            log.warn("Login fallido: usuario inactivo");
            throw new BadCredentialsException(ERROR_CREDENCIALES);
        }

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            log.warn("Login fallido: credenciales incorrectas");
            throw new BadCredentialsException(ERROR_CREDENCIALES);
        }

        String rolCodigo = (user.getRol() != null && user.getRol().getCodigo() != null)
                ? user.getRol().getCodigo().trim().toUpperCase(Locale.ROOT)
                : "";

        if (!"ADMINISTRADOR".equals(rolCodigo) && !"OPERADOR".equals(rolCodigo)) {
            log.warn("Login denegado: rol {} no autorizado para esta plataforma", rolCodigo);
            throw new BadCredentialsException(ERROR_CREDENCIALES);
        }

        // Crear/cambiar id de sesión para prevenir Session Fixation
        HttpSession session = httpRequest.getSession(true);
        httpRequest.changeSessionId();

        // Generar token CSRF seguro y almacenarlo en la sesión
        String csrfToken = UUID.randomUUID().toString().replace("-", "");
        session.setAttribute("CSRF_TOKEN", csrfToken);

        // Configurar Authentication en SecurityContext y persistir en la sesión
        List<SimpleGrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority(rolCodigo),
                new SimpleGrantedAuthority("ROLE_" + rolCodigo)
        );
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user.getUsername(), null, authorities);

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);

        // Actualizar último acceso
        user.setUltimoAcceso(OffsetDateTime.now());
        usuarioRepository.save(user);

        // Mapear rol visible según el contrato del frontend ("Administrador" o "Operador")
        String displayRol = "ADMINISTRADOR".equals(rolCodigo) ? "Administrador" : "Operador";

        log.info("Login exitoso para usuario: {} con rol: {}", user.getUsername(), displayRol);

        return ResponseEntity.ok(new AuthLoginResponse(
                new AuthUserResponse(user.getIdUsuario(), user.getUsername(), displayRol),
                csrfToken
        ));
    }

    @GetMapping("/me")
    public ResponseEntity<AuthMeResponse> me(HttpServletRequest httpRequest) {
        // getSession(false): no crear sesión ni Set-Cookie si el visitante es anónimo
        HttpSession session = httpRequest.getSession(false);
        SecurityContext securityContext = (session != null)
                ? (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY)
                : null;

        Authentication authentication = securityContext != null ? securityContext.getAuthentication() : null;
        if (session == null || authentication == null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            return ResponseEntity.ok(AuthMeResponse.unauthenticated());
        }

        String username = authentication.getName();
        Optional<UsuarioEntity> optUser = usuarioRepository.findByUsername(username);
        if (optUser.isEmpty() || !optUser.get().isActivo()) {
            return ResponseEntity.ok(AuthMeResponse.unauthenticated());
        }

        UsuarioEntity user = optUser.get();
        String csrfToken = (String) session.getAttribute("CSRF_TOKEN");
        if (csrfToken == null) {
            csrfToken = UUID.randomUUID().toString().replace("-", "");
            session.setAttribute("CSRF_TOKEN", csrfToken);
        }

        String rolCodigo = (user.getRol() != null && user.getRol().getCodigo() != null)
                ? user.getRol().getCodigo().trim().toUpperCase(Locale.ROOT)
                : "";
        String displayRol = "ADMINISTRADOR".equals(rolCodigo) ? "Administrador" : "Operador";

        return ResponseEntity.ok(AuthMeResponse.authenticated(
                new AuthUserResponse(user.getIdUsuario(), user.getUsername(), displayRol),
                csrfToken
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest httpRequest) {
        log.info("Cierre de sesión solicitado");
        HttpSession session = httpRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
        return ResponseEntity.noContent().build();
    }
}
