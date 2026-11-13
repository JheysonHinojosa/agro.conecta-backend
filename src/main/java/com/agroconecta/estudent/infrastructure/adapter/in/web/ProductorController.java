package com.agroconecta.estudent.infrastructure.adapter.in.web;

import com.agroconecta.estudent.application.dto.request.ProductorRequest;
import com.agroconecta.estudent.application.dto.response.ProductorResponse;
import com.agroconecta.estudent.application.services.ProductorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productor")
@RequiredArgsConstructor
@Slf4j
public class ProductorController {

    private final ProductorService productorService;

    @PostMapping
    public ResponseEntity<ProductorResponse> crear(@Valid @RequestBody ProductorRequest request) {
        log.info("Creando nuevo productor");
        return ResponseEntity.status(HttpStatus.CREATED).body(productorService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<ProductorResponse>> listar() {
        log.info("Listando productores");
        return ResponseEntity.ok(productorService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductorResponse> buscarPorId(@PathVariable Long id) {
        log.info("Buscando productor con id: {}", id);
        return ResponseEntity.ok(productorService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductorResponse> actualizar(@PathVariable Long id,
                                                        @Valid @RequestBody ProductorRequest request) {
        log.info("Actualizando productor con id: {}", id);
        return ResponseEntity.ok(productorService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Eliminando productor con id: {}", id);
        productorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}