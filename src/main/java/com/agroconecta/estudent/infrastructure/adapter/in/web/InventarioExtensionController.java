package com.agroconecta.estudent.infrastructure.adapter.in.web;

import com.agroconecta.estudent.application.dto.request.StockMinimoRequest;
import com.agroconecta.estudent.application.dto.response.InventarioResponse;
import com.agroconecta.estudent.application.services.InventarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventario")
@RequiredArgsConstructor
@Slf4j
public class InventarioExtensionController {

    private final InventarioService inventarioService;

    @PatchMapping("/{id}/minimo")
    public ResponseEntity<InventarioResponse> actualizarMinimo(
            @PathVariable Long id,
            @Valid @RequestBody StockMinimoRequest request) {
        log.info("Actualizando stock mínimo para inventario id: {} a {}", id, request.stockMinimo());
        InventarioResponse response = inventarioService.actualizarMinimo(id, request.stockMinimo());
        return ResponseEntity.ok(response);
    }
}
