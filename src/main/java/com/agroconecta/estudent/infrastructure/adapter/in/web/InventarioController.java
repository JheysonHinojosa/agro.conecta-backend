package com.agroconecta.estudent.infrastructure.adapter.in.web;

import com.agroconecta.estudent.application.dto.request.InventarioRequest;
import com.agroconecta.estudent.application.dto.response.InventarioResponse;
import com.agroconecta.estudent.application.services.InventarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
@RequiredArgsConstructor
@Slf4j
public class InventarioController {

    private final InventarioService inventarioService;

    @PostMapping
    public ResponseEntity<InventarioResponse> crear(@Valid @RequestBody InventarioRequest request) {
        log.info("Creando nuevo inventario");
        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<InventarioResponse>> listar() {
        log.info("Listando inventarios");
        return ResponseEntity.ok(inventarioService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventarioResponse> buscarPorId(@PathVariable Long id) {
        log.info("Buscando inventario con id: {}", id);
        return ResponseEntity.ok(inventarioService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventarioResponse> actualizar(@PathVariable Long id,
                                                         @Valid @RequestBody InventarioRequest request) {
        log.info("Actualizando inventario con id: {}", id);
        return ResponseEntity.ok(inventarioService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Eliminando inventario con id: {}", id);
        inventarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}