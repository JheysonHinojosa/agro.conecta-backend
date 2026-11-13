package com.agroconecta.estudent.infrastructure.adapter.in.web;

import com.agroconecta.estudent.application.dto.request.SensorIOTRequest;
import com.agroconecta.estudent.application.dto.response.SensorIOTResponse;
import com.agroconecta.estudent.application.services.SensorIOTService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensor-iot")
@RequiredArgsConstructor
@Slf4j
public class SensorIOTController {

    private final SensorIOTService sensorIOTService;

    @PostMapping
    public ResponseEntity<SensorIOTResponse> crear(@Valid @RequestBody SensorIOTRequest request) {
        log.info("Creando nuevo sensor IoT");
        return ResponseEntity.status(HttpStatus.CREATED).body(sensorIOTService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<SensorIOTResponse>> listar() {
        log.info("Listando sensores IoT");
        return ResponseEntity.ok(sensorIOTService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorIOTResponse> buscarPorId(@PathVariable Long id) {
        log.info("Buscando sensor IoT con id: {}", id);
        return ResponseEntity.ok(sensorIOTService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SensorIOTResponse> actualizar(@PathVariable Long id,
                                                        @Valid @RequestBody SensorIOTRequest request) {
        log.info("Actualizando sensor IoT con id: {}", id);
        return ResponseEntity.ok(sensorIOTService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("Eliminando sensor IoT con id: {}", id);
        sensorIOTService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}