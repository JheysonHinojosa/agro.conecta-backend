package com.agroconecta.estudent.infrastructure.adapter.in.web;

import com.agroconecta.estudent.application.dto.request.ParTipoDocumentoRequest;
import com.agroconecta.estudent.application.dto.response.ParTipoDocumentoResponse;
import com.agroconecta.estudent.application.services.ParTipoDocumentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/par-tipo-documento")
@RequiredArgsConstructor
@Slf4j
public class ParTipoDocumentoController {

    private final ParTipoDocumentoService parTipoDocumentoService;

    @PostMapping
    public ResponseEntity<ParTipoDocumentoResponse> crear(
            @Valid @RequestBody ParTipoDocumentoRequest request) {
        log.info("Creando nuevo tipo de documento");
        return ResponseEntity.status(HttpStatus.CREATED).body(parTipoDocumentoService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<ParTipoDocumentoResponse>> listar() {
        log.info("Listando tipos de documento");
        return ResponseEntity.ok(parTipoDocumentoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParTipoDocumentoResponse> buscarPorId(@PathVariable Integer id) {
        log.info("Buscando tipo de documento con id: {}", id);
        return ResponseEntity.ok(parTipoDocumentoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParTipoDocumentoResponse> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody ParTipoDocumentoRequest request) {
        log.info("Actualizando tipo de documento con id: {}", id);
        return ResponseEntity.ok(parTipoDocumentoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("Eliminando tipo de documento con id: {}", id);
        parTipoDocumentoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}