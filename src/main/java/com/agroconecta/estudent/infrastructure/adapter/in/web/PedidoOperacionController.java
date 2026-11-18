package com.agroconecta.estudent.infrastructure.adapter.in.web;

import com.agroconecta.estudent.application.dto.request.PedidoOperacionEstadoRequest;
import com.agroconecta.estudent.application.dto.request.PedidoOperacionRequest;
import com.agroconecta.estudent.application.dto.response.PedidoOperacionResponse;
import com.agroconecta.estudent.application.services.PedidoOperacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedido-operacion")
@RequiredArgsConstructor
@Slf4j
public class PedidoOperacionController {

    private final PedidoOperacionService pedidoOperacionService;

    @PostMapping
    public ResponseEntity<PedidoOperacionResponse> crear(@Valid @RequestBody PedidoOperacionRequest request) {
        log.info("Creando pedido con detalles y descuento atómico de inventario");
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoOperacionService.crear(request));
    }

    @PatchMapping("/{idPedido}/estado")
    public ResponseEntity<PedidoOperacionResponse> cambiarEstado(
            @PathVariable Long idPedido,
            @Valid @RequestBody PedidoOperacionEstadoRequest request) {
        log.info("Cambiando estado del pedido {} mediante operación transaccional", idPedido);
        return ResponseEntity.ok(pedidoOperacionService.cambiarEstado(idPedido, request));
    }
}
