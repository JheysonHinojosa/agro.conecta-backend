package com.agroconecta.estudent.infrastructure.adapter.in.web;

import com.agroconecta.estudent.application.dto.request.DetallePedidoRequest;
import com.agroconecta.estudent.application.dto.response.DetallePedidoResponse;
import com.agroconecta.estudent.application.services.DetallePedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalle-pedido")
@RequiredArgsConstructor
@Slf4j
public class DetallePedidoController {

    private final DetallePedidoService detallePedidoService;

    @PostMapping
    public ResponseEntity<DetallePedidoResponse> crear(@Valid @RequestBody DetallePedidoRequest request) {
        log.info("Creando nuevo detalle de pedido");
        return ResponseEntity.status(HttpStatus.CREATED).body(detallePedidoService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<DetallePedidoResponse>> listar() {
        log.info("Listando detalles de pedido");
        return ResponseEntity.ok(detallePedidoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallePedidoResponse> buscarPorId(@PathVariable Long id) {
        log.info("Buscando detalle de pedido con id: {}", id);
        return ResponseEntity.ok(detallePedidoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetallePedidoResponse> actualizar(@PathVariable Long id,
                                                            @Valid @RequestBody DetallePedidoRequest request) {
        log.info("Actualizando detalle de pedido con id: {}", id);
        return ResponseEntity.ok(detallePedidoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Eliminando detalle de pedido con id: {}", id);
        detallePedidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}