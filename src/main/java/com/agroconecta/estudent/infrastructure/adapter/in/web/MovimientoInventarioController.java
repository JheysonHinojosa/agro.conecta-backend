package com.agroconecta.estudent.infrastructure.adapter.in.web;

import com.agroconecta.estudent.application.dto.request.MovimientoInventarioRequest;
import com.agroconecta.estudent.application.dto.response.MovimientoInventarioResponse;
import com.agroconecta.estudent.application.services.MovimientoInventarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimiento-inventario")
@RequiredArgsConstructor
@Slf4j
public class MovimientoInventarioController {

    private final MovimientoInventarioService movimientoInventarioService;

    @PostMapping
    public ResponseEntity<MovimientoInventarioResponse> crear(
            @Valid @RequestBody MovimientoInventarioRequest request) {
        log.info("Registrando movimiento de inventario");
        return ResponseEntity.status(HttpStatus.CREATED).body(movimientoInventarioService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<MovimientoInventarioResponse>> listar() {
        log.info("Listando movimientos de inventario");
        return ResponseEntity.ok(movimientoInventarioService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoInventarioResponse> buscarPorId(@PathVariable Long id) {
        log.info("Buscando movimiento de inventario con id: {}", id);
        return ResponseEntity.ok(movimientoInventarioService.buscarPorId(id));
    }
}