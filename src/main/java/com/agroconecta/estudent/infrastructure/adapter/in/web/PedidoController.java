package com.agroconecta.estudent.infrastructure.adapter.in.web;

import com.agroconecta.estudent.application.dto.request.CambioEstadoPedidoRequest;
import com.agroconecta.estudent.application.dto.request.PedidoRequest;
import com.agroconecta.estudent.application.dto.response.PedidoResponse;
import com.agroconecta.estudent.application.services.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedido")
@RequiredArgsConstructor
@Slf4j
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoResponse> crear(@Valid @RequestBody PedidoRequest request) {
        log.info("Creando nuevo pedido");
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> listar() {
        log.info("Listando pedidos");
        return ResponseEntity.ok(pedidoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> buscarPorId(@PathVariable Long id) {
        log.info("Buscando pedido con id: {}", id);
        return ResponseEntity.ok(pedidoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponse> actualizar(@PathVariable Long id,
                                                     @Valid @RequestBody PedidoRequest request) {
        log.info("Actualizando pedido con id: {}", id);
        return ResponseEntity.ok(pedidoService.actualizar(id, request));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<PedidoResponse> cambiarEstado(@PathVariable Long id,
                                                        @Valid @RequestBody CambioEstadoPedidoRequest request) {
        log.info("Cambiando estado del pedido con id: {}", id);
        return ResponseEntity.ok(pedidoService.cambiarEstado(id, request));
    }
}