package com.agroconecta.estudent.infrastructure.adapter.in.web;

import com.agroconecta.estudent.application.dto.request.ParTipoMovimientoRequest;
import com.agroconecta.estudent.application.dto.response.ParTipoMovimientoResponse;
import com.agroconecta.estudent.application.services.ParTipoMovimientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/par-tipo-movimiento")
@RequiredArgsConstructor
@Slf4j
public class ParTipoMovimientoController {

    private final ParTipoMovimientoService parTipoMovimientoService;

    @PostMapping
    public ResponseEntity<ParTipoMovimientoResponse> crear(
            @Valid @RequestBody ParTipoMovimientoRequest request) {
        log.info("Creando nuevo tipo de movimiento");
        return ResponseEntity.status(HttpStatus.CREATED).body(parTipoMovimientoService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<ParTipoMovimientoResponse>> listar() {
        log.info("Listando tipos de movimiento");
        return ResponseEntity.ok(parTipoMovimientoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParTipoMovimientoResponse> buscarPorId(@PathVariable Integer id) {
        log.info("Buscando tipo de movimiento con id: {}", id);
        return ResponseEntity.ok(parTipoMovimientoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParTipoMovimientoResponse> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody ParTipoMovimientoRequest request) {
        log.info("Actualizando tipo de movimiento con id: {}", id);
        return ResponseEntity.ok(parTipoMovimientoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("Eliminando tipo de movimiento con id: {}", id);
        parTipoMovimientoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}