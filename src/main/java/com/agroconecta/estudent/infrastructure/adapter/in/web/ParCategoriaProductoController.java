package com.agroconecta.estudent.infrastructure.adapter.in.web;

import com.agroconecta.estudent.application.dto.request.ParCategoriaProductoRequest;
import com.agroconecta.estudent.application.dto.response.ParCategoriaProductoResponse;
import com.agroconecta.estudent.application.services.ParCategoriaProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/par-categoria-producto")
@RequiredArgsConstructor
@Slf4j
public class ParCategoriaProductoController {

    private final ParCategoriaProductoService parCategoriaProductoService;

    @PostMapping
    public ResponseEntity<ParCategoriaProductoResponse> crear(
            @Valid @RequestBody ParCategoriaProductoRequest request) {
        log.info("Creando nueva categoría de producto");
        return ResponseEntity.status(HttpStatus.CREATED).body(parCategoriaProductoService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<ParCategoriaProductoResponse>> listar() {
        log.info("Listando categorías de producto");
        return ResponseEntity.ok(parCategoriaProductoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParCategoriaProductoResponse> buscarPorId(@PathVariable Integer id) {
        log.info("Buscando categoría de producto con id: {}", id);
        return ResponseEntity.ok(parCategoriaProductoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParCategoriaProductoResponse> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody ParCategoriaProductoRequest request) {
        log.info("Actualizando categoría de producto con id: {}", id);
        return ResponseEntity.ok(parCategoriaProductoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("Eliminando categoría de producto con id: {}", id);
        parCategoriaProductoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}