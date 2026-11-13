package com.agroconecta.estudent.infrastructure.adapter.in.web;

import com.agroconecta.estudent.application.dto.request.ParMonedaRequest;
import com.agroconecta.estudent.application.dto.response.ParMonedaResponse;
import com.agroconecta.estudent.application.services.ParMonedaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/par-moneda")
@RequiredArgsConstructor
@Slf4j
public class ParMonedaController {

    private final ParMonedaService parMonedaService;

    @PostMapping
    public ResponseEntity<ParMonedaResponse> crear(@Valid @RequestBody ParMonedaRequest request) {
        log.info("Creando nueva moneda");
        return ResponseEntity.status(HttpStatus.CREATED).body(parMonedaService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<ParMonedaResponse>> listar() {
        log.info("Listando monedas");
        return ResponseEntity.ok(parMonedaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParMonedaResponse> buscarPorId(@PathVariable Integer id) {
        log.info("Buscando moneda con id: {}", id);
        return ResponseEntity.ok(parMonedaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParMonedaResponse> actualizar(@PathVariable Integer id,
                                                        @Valid @RequestBody ParMonedaRequest request) {
        log.info("Actualizando moneda con id: {}", id);
        return ResponseEntity.ok(parMonedaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("Eliminando moneda con id: {}", id);
        parMonedaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}