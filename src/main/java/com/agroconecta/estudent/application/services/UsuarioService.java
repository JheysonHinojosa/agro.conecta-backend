package com.agroconecta.estudent.application.services;

import com.agroconecta.estudent.application.dto.request.UsuarioRequest;
import com.agroconecta.estudent.application.dto.response.UsuarioResponse;

import java.util.List;

public interface UsuarioService {

    UsuarioResponse crear(UsuarioRequest request);

    List<UsuarioResponse> listar();

    UsuarioResponse buscarPorId(Long id);

    UsuarioResponse actualizar(Long id, UsuarioRequest request);

    void eliminar(Long id);
}