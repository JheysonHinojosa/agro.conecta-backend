package com.agroconecta.estudent.infrastructure.adapter.in.web;

import com.agroconecta.estudent.application.dto.request.AlertaRequest;
import com.agroconecta.estudent.application.dto.request.UsuarioRequest;
import com.agroconecta.estudent.application.dto.response.AlertaResponse;
import com.agroconecta.estudent.application.services.AlertaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/alerta")
@RequiredArgsConstructor
@Slf4j
public class AlertaController {

    private final AlertaService alertaService;

    @PostMapping
    public ResponseEntity<AlertaResponse> crear(@Valid @RequestBody AlertaRequest request){

        log.info("Creando nueva alerta: {}", request);
        AlertaResponse response = alertaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AlertaResponse>> listar(){

        log.info("Listando todos los alerta");
        List<AlertaResponse> response = alertaService.listar();
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<AlertaResponse> buscarPorId(@PathVariable Integer id){
        log.info("Buscando nueva alerta: {}", id);

        AlertaResponse response = alertaService.buscarPorId(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<AlertaResponse> actualizar(@PathVariable Integer id, @Valid @RequestBody AlertaRequest request){
        log.info("Actualizando alerta con id: {}", id);
        AlertaResponse response = alertaService.actualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AlertaResponse> eliminar(@PathVariable Integer id){
        log.info("Eliminando alerta con id: {}", id);
        alertaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
