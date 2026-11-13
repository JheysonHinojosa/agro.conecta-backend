package com.agroconecta.estudent.infrastructure.adapter.in.web;

import com.agroconecta.estudent.application.dto.request.ParTipoCompradorRequest;
import com.agroconecta.estudent.application.dto.response.ParTipoCompradorResponse;
import com.agroconecta.estudent.application.services.ParTipoCompradorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/par-tipo-comprador")
@RequiredArgsConstructor
@Slf4j
public class ParTipoCompradorController {

    private final ParTipoCompradorService parTipoCompradorService;

    @PostMapping
    public ResponseEntity<ParTipoCompradorResponse> crear(
            @Valid @RequestBody ParTipoCompradorRequest request) {
        log.info("Creando nuevo tipo de comprador");
        return ResponseEntity.status(HttpStatus.CREATED).body(parTipoCompradorService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<ParTipoCompradorResponse>> listar() {
        log.info("Listando tipos de comprador");
        return ResponseEntity.ok(parTipoCompradorService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParTipoCompradorResponse> buscarPorId(@PathVariable Integer id) {
        log.info("Buscando tipo de comprador con id: {}", id);
        return ResponseEntity.ok(parTipoCompradorService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParTipoCompradorResponse> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody ParTipoCompradorRequest request) {
        log.info("Actualizando tipo de comprador con id: {}", id);
        return ResponseEntity.ok(parTipoCompradorService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("Eliminando tipo de comprador con id: {}", id);
        parTipoCompradorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}