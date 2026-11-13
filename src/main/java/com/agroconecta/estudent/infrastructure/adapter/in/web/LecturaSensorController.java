package com.agroconecta.estudent.infrastructure.adapter.in.web;

import com.agroconecta.estudent.application.dto.request.LecturaSensorRequest;
import com.agroconecta.estudent.application.dto.response.LecturaSensorResponse;
import com.agroconecta.estudent.application.services.LecturaSensorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/lectura_sensor")
@RequiredArgsConstructor
@Slf4j
public class LecturaSensorController {

    private final LecturaSensorService lecturaSensorService;

    @PostMapping
    public ResponseEntity<LecturaSensorResponse> crear(@Valid @RequestBody LecturaSensorRequest request){

        log.info("Creando nueva lectura de sensor: {}", request);
        LecturaSensorResponse response = lecturaSensorService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<LecturaSensorResponse>> listar(){

        log.info("Listando todos las lectura de sensores");
        List<LecturaSensorResponse> response = lecturaSensorService.listar();
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<LecturaSensorResponse> buscarPorId(@PathVariable Integer id){
        log.info("Buscando nueva lectura de sensor: {}", id);

        LecturaSensorResponse response = lecturaSensorService.buscarPorId(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<LecturaSensorResponse> actualizar(@PathVariable Integer id, @Valid @RequestBody LecturaSensorRequest request){
        log.info("Actualizando la lectura de sensor con id: {}", id);
        LecturaSensorResponse response = lecturaSensorService.actualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<LecturaSensorResponse> eliminar(@PathVariable Integer id){
        log.info("Eliminando la lectura de sensor: {}", id);
        lecturaSensorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
