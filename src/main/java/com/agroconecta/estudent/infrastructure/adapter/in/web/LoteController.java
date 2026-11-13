package com.agroconecta.estudent.infrastructure.adapter.in.web;

import com.agroconecta.estudent.application.dto.request.LoteRequest;
import com.agroconecta.estudent.application.dto.response.LoteResponse;
import com.agroconecta.estudent.application.services.LoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lote")
@RequiredArgsConstructor
@Slf4j
public class LoteController {

    private final LoteService loteService;

    @PostMapping
    public ResponseEntity<LoteResponse> crear(@Valid @RequestBody LoteRequest request) {
        log.info("Creando nuevo lote");
        return ResponseEntity.status(HttpStatus.CREATED).body(loteService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<LoteResponse>> listar() {
        log.info("Listando lotes");
        return ResponseEntity.ok(loteService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoteResponse> buscarPorId(@PathVariable Long id) {
        log.info("Buscando lote con id: {}", id);
        return ResponseEntity.ok(loteService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoteResponse> actualizar(@PathVariable Long id,
                                                   @Valid @RequestBody LoteRequest request) {
        log.info("Actualizando lote con id: {}", id);
        return ResponseEntity.ok(loteService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Eliminando lote con id: {}", id);
        loteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}