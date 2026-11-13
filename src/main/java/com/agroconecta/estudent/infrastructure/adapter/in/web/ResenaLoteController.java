package com.agroconecta.estudent.infrastructure.adapter.in.web;

import com.agroconecta.estudent.application.dto.request.ResenaLoteRequest;
import com.agroconecta.estudent.application.dto.request.ResenaLoteUpdateRequest;
import com.agroconecta.estudent.application.dto.response.ResenaLoteResponse;
import com.agroconecta.estudent.application.services.ResenaLoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resena-lote")
@RequiredArgsConstructor
@Slf4j
public class ResenaLoteController {

    private final ResenaLoteService resenaLoteService;

    @PostMapping
    public ResponseEntity<ResenaLoteResponse> crear(@Valid @RequestBody ResenaLoteRequest request) {
        log.info("Creando nueva reseña de lote");
        return ResponseEntity.status(HttpStatus.CREATED).body(resenaLoteService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<ResenaLoteResponse>> listar() {
        log.info("Listando reseñas de lote");
        return ResponseEntity.ok(resenaLoteService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResenaLoteResponse> buscarPorId(@PathVariable Long id) {
        log.info("Buscando reseña de lote con id: {}", id);
        return ResponseEntity.ok(resenaLoteService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResenaLoteResponse> actualizar(@PathVariable Long id,
                                                         @Valid @RequestBody ResenaLoteUpdateRequest request) {
        log.info("Actualizando reseña de lote con id: {}", id);
        return ResponseEntity.ok(resenaLoteService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Eliminando reseña de lote con id: {}", id);
        resenaLoteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}