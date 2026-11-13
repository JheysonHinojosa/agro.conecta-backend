package com.agroconecta.estudent.infrastructure.adapter.in.web;

import com.agroconecta.estudent.application.dto.request.ParRolRequest;
import com.agroconecta.estudent.application.dto.response.ParRolResponse;
import com.agroconecta.estudent.application.services.ParRolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/par-rol")
@RequiredArgsConstructor
@Slf4j
public class ParRolController {

    private final ParRolService parRolService;

    @PostMapping
    public ResponseEntity<ParRolResponse> crear(@Valid @RequestBody ParRolRequest request) {
        log.info("Creando nuevo rol");
        return ResponseEntity.status(HttpStatus.CREATED).body(parRolService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<ParRolResponse>> listar() {
        log.info("Listando roles");
        return ResponseEntity.ok(parRolService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParRolResponse> buscarPorId(@PathVariable Integer id) {
        log.info("Buscando rol con id: {}", id);
        return ResponseEntity.ok(parRolService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParRolResponse> actualizar(@PathVariable Integer id,
                                                     @Valid @RequestBody ParRolRequest request) {
        log.info("Actualizando rol con id: {}", id);
        return ResponseEntity.ok(parRolService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("Eliminando rol con id: {}", id);
        parRolService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}