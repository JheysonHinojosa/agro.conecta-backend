package com.agroconecta.estudent.infrastructure.adapter.in.web;

import com.agroconecta.estudent.application.dto.request.CentroAcopioRequest;
import com.agroconecta.estudent.application.dto.response.CentroAcopioResponse;
import com.agroconecta.estudent.application.services.CentroAcopioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/centro_acopio")
@RequiredArgsConstructor
@Slf4j
public class CentroAcopioController {

    private final CentroAcopioService centroAcopioService;

    @PostMapping
    public ResponseEntity<CentroAcopioResponse> crear(@Valid @RequestBody CentroAcopioRequest request){

        log.info("Creando nuevo Centro Acopio: {}", request);
        CentroAcopioResponse response = centroAcopioService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CentroAcopioResponse>> listar(){

        log.info("Listando todos los Centros Acopios");
        List<CentroAcopioResponse> response = centroAcopioService.listar();
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<CentroAcopioResponse> buscarPorId(@PathVariable Integer id){
        log.info("Buscando nuevo Centro Acopio: {}", id);

        CentroAcopioResponse response = centroAcopioService.buscarPorId(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<CentroAcopioResponse> actualizar(@PathVariable Integer id, @Valid @RequestBody CentroAcopioRequest request){
        log.info("Actualizando Centro Acopio con id: {}", id);
        CentroAcopioResponse response = centroAcopioService.actualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CentroAcopioResponse> eliminar(@PathVariable Integer id){
        log.info("Eliminando Centro Acopio con id: {}", id);
        centroAcopioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
