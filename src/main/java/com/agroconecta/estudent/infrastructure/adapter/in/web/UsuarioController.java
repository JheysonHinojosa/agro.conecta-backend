package com.agroconecta.estudent.infrastructure.adapter.in.web;

import com.agroconecta.estudent.application.dto.request.UsuarioRequest;
import com.agroconecta.estudent.application.dto.response.UsuarioResponse;
import com.agroconecta.estudent.application.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/usuario")
@RequiredArgsConstructor
@Slf4j
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponse> crear(@Valid @RequestBody UsuarioRequest request){

        log.info("Creando nuevo usuario: {}", request);
        UsuarioResponse response = usuarioService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listar(){

        log.info("Listando todos los usuarios");
        List<UsuarioResponse> response = usuarioService.listar();
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Integer id){
        log.info("Buscando nuevo usuario: {}", id);

        UsuarioResponse response = usuarioService.buscarPorId(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<UsuarioResponse> actualizar(@PathVariable Integer id, @Valid @RequestBody UsuarioRequest request){
        log.info("Actualizando usuario con id: {}", id);
        UsuarioResponse response = usuarioService.actualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UsuarioResponse> eliminar(@PathVariable Integer id){
        log.info("Eliminando usuario con id: {}", id);
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
