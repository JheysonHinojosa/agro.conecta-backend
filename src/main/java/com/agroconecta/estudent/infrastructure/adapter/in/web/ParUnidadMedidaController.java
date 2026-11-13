package com.agroconecta.estudent.infrastructure.adapter.in.web;

import com.agroconecta.estudent.application.dto.request.ParUnidadMedidaRequest;
import com.agroconecta.estudent.application.dto.response.ParUnidadMedidaResponse;
import com.agroconecta.estudent.application.services.ParUnidadMedidaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/par-unidad-medida")
@RequiredArgsConstructor
@Slf4j
public class ParUnidadMedidaController {

    private final ParUnidadMedidaService parUnidadMedidaService;

    @PostMapping
    public ResponseEntity<ParUnidadMedidaResponse> crear(
            @Valid @RequestBody ParUnidadMedidaRequest request) {
        log.info("Creando nueva unidad de medida");
        return ResponseEntity.status(HttpStatus.CREATED).body(parUnidadMedidaService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<ParUnidadMedidaResponse>> listar() {
        log.info("Listando unidades de medida");
        return ResponseEntity.ok(parUnidadMedidaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParUnidadMedidaResponse> buscarPorId(@PathVariable Integer id) {
        log.info("Buscando unidad de medida con id: {}", id);
        return ResponseEntity.ok(parUnidadMedidaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParUnidadMedidaResponse> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody ParUnidadMedidaRequest request) {
        log.info("Actualizando unidad de medida con id: {}", id);
        return ResponseEntity.ok(parUnidadMedidaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("Eliminando unidad de medida con id: {}", id);
        parUnidadMedidaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}