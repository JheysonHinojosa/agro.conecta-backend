package com.agroconecta.estudent.infrastructure.adapter.in.web;

import com.agroconecta.estudent.application.dto.request.ParEstadoPedidoRequest;
import com.agroconecta.estudent.application.dto.response.ParEstadoPedidoResponse;
import com.agroconecta.estudent.application.services.ParEstadoPedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/par-estado-pedido")
@RequiredArgsConstructor
@Slf4j
public class ParEstadoPedidoController {

    private final ParEstadoPedidoService parEstadoPedidoService;

    @PostMapping
    public ResponseEntity<ParEstadoPedidoResponse> crear(@Valid @RequestBody ParEstadoPedidoRequest request) {
        log.info("Creando nuevo estado de pedido");
        return ResponseEntity.status(HttpStatus.CREATED).body(parEstadoPedidoService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<ParEstadoPedidoResponse>> listar() {
        log.info("Listando estados de pedido");
        return ResponseEntity.ok(parEstadoPedidoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParEstadoPedidoResponse> buscarPorId(@PathVariable Integer id) {
        log.info("Buscando estado de pedido con id: {}", id);
        return ResponseEntity.ok(parEstadoPedidoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParEstadoPedidoResponse> actualizar(@PathVariable Integer id,
                                                              @Valid @RequestBody ParEstadoPedidoRequest request) {
        log.info("Actualizando estado de pedido con id: {}", id);
        return ResponseEntity.ok(parEstadoPedidoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("Eliminando estado de pedido con id: {}", id);
        parEstadoPedidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}