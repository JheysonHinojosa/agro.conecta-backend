package com.agroconecta.estudent.infrastructure.adapter.in.web;

import com.agroconecta.estudent.application.dto.request.ParPaisRequest;
import com.agroconecta.estudent.application.dto.response.ParPaisResponse;
import com.agroconecta.estudent.application.services.ParPaisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/par-pais")
@RequiredArgsConstructor
@Slf4j
public class ParPaisController {

    private final ParPaisService parPaisService;

    @PostMapping
    public ResponseEntity<ParPaisResponse> crear(@Valid @RequestBody ParPaisRequest request) {
        log.info("Creando nuevo país");
        return ResponseEntity.status(HttpStatus.CREATED).body(parPaisService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<ParPaisResponse>> listar() {
        log.info("Listando países");
        return ResponseEntity.ok(parPaisService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParPaisResponse> buscarPorId(@PathVariable Integer id) {
        log.info("Buscando país con id: {}", id);
        return ResponseEntity.ok(parPaisService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParPaisResponse> actualizar(@PathVariable Integer id,
                                                      @Valid @RequestBody ParPaisRequest request) {
        log.info("Actualizando país con id: {}", id);
        return ResponseEntity.ok(parPaisService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("Eliminando país con id: {}", id);
        parPaisService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}