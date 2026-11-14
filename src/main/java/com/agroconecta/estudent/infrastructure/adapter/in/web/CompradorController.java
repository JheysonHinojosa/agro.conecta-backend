package com.agroconecta.estudent.infrastructure.adapter.in.web;

import com.agroconecta.estudent.application.dto.request.CompradorRequest;
import com.agroconecta.estudent.application.dto.response.CompradorResponse;
import com.agroconecta.estudent.application.services.CompradorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comprador")
@RequiredArgsConstructor
@Slf4j
public class CompradorController {

    private final CompradorService compradorService;

    @PostMapping
    public ResponseEntity<CompradorResponse> crear(@Valid @RequestBody CompradorRequest request){

        log.info("Creando nuevo comprador: {}", request);
        CompradorResponse response = compradorService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CompradorResponse>> listar(){

        log.info("Listando todos los compradores");
        List<CompradorResponse> response = compradorService.listar();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompradorResponse> buscarPorId(@PathVariable Long id) {
        log.info("Buscando comprador: {}", id);
        CompradorResponse response = compradorService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompradorResponse> actualizar(@PathVariable Long id, @Valid @RequestBody CompradorRequest request) {
        log.info("Actualizando comprador con id: {}", id);
        CompradorResponse response = compradorService.actualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Eliminando comprador con id: {}", id);
        compradorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
