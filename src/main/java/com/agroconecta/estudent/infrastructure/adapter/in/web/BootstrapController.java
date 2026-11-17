package com.agroconecta.estudent.infrastructure.adapter.in.web;

import com.agroconecta.estudent.application.dto.response.BootstrapResponse;
import com.agroconecta.estudent.application.services.BootstrapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bootstrap")
@RequiredArgsConstructor
@Slf4j
public class BootstrapController {

    private final BootstrapService bootstrapService;

    @GetMapping
    public ResponseEntity<BootstrapResponse> obtenerBootstrap(Authentication authentication) {
        log.info("GET /api/bootstrap solicitado por: {}", authentication != null ? authentication.getName() : "anónimo");
        BootstrapResponse snapshot = bootstrapService.obtenerSnapshot(authentication);
        return ResponseEntity.ok(snapshot);
    }
}
