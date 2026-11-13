package com.agroconecta.estudent.infrastructure.adapter.in.web;

import com.agroconecta.estudent.application.dto.request.ParTipoAlertaRequest;
import com.agroconecta.estudent.application.dto.response.ParTipoAlertaResponse;
import com.agroconecta.estudent.application.services.ParTipoAlertaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/par-tipo-alerta")
@RequiredArgsConstructor
@Slf4j
public class ParTipoAlertaController {

    private final ParTipoAlertaService parTipoAlertaService;

    @PostMapping
    public ResponseEntity<ParTipoAlertaResponse> crear(@Valid @RequestBody ParTipoAlertaRequest request) {
        log.info("Creando nuevo tipo de alerta");
        return ResponseEntity.status(HttpStatus.CREATED).body(parTipoAlertaService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<ParTipoAlertaResponse>> listar() {
        log.info("Listando tipos de alerta");
        return ResponseEntity.ok(parTipoAlertaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParTipoAlertaResponse> buscarPorId(@PathVariable Integer id) {
        log.info("Buscando tipo de alerta con id: {}", id);
        return ResponseEntity.ok(parTipoAlertaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParTipoAlertaResponse> actualizar(@PathVariable Integer id,
                                                            @Valid @RequestBody ParTipoAlertaRequest request) {
        log.info("Actualizando tipo de alerta con id: {}", id);
        return ResponseEntity.ok(parTipoAlertaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("Eliminando tipo de alerta con id: {}", id);
        parTipoAlertaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}