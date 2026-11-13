package com.agroconecta.estudent.application.services;

import com.agroconecta.estudent.application.dto.request.InventarioRequest;
import com.agroconecta.estudent.application.dto.response.InventarioResponse;

import java.util.List;

public interface InventarioService {

    InventarioResponse crear(InventarioRequest request);

    List<InventarioResponse> listar();

    InventarioResponse buscarPorId(Long id);

    InventarioResponse actualizar(Long id, InventarioRequest request);

    void eliminar(Long id);
}