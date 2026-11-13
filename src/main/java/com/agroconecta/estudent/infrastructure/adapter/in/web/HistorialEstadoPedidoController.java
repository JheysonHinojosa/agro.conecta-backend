package com.agroconecta.estudent.infrastructure.adapter.in.web;

import com.agroconecta.estudent.application.dto.request.HistorialEstadoPedidoRequest;
import com.agroconecta.estudent.application.dto.response.HistorialEstadoPedidoResponse;
import com.agroconecta.estudent.application.services.HistorialEstadoPedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historial-estado-pedido")
@RequiredArgsConstructor
@Slf4j
public class HistorialEstadoPedidoController {

    private final HistorialEstadoPedidoService historialEstadoPedidoService;

    @PostMapping
    public ResponseEntity<HistorialEstadoPedidoResponse> crear(
            @Valid @RequestBody HistorialEstadoPedidoRequest request) {
        log.info("Creando nuevo historial de estado de pedido");
        return ResponseEntity.status(HttpStatus.CREATED).body(historialEstadoPedidoService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<HistorialEstadoPedidoResponse>> listar() {
        log.info("Listando historiales de estado de pedido");
        return ResponseEntity.ok(historialEstadoPedidoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialEstadoPedidoResponse> buscarPorId(@PathVariable Long id) {
        log.info("Buscando historial de estado de pedido con id: {}", id);
        return ResponseEntity.ok(historialEstadoPedidoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistorialEstadoPedidoResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody HistorialEstadoPedidoRequest request) {
        log.info("Actualizando historial de estado de pedido con id: {}", id);
        return ResponseEntity.ok(historialEstadoPedidoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Eliminando historial de estado de pedido con id: {}", id);
        historialEstadoPedidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}