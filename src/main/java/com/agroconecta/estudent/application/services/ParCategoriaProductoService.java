package com.agroconecta.estudent.application.services;

import com.agroconecta.estudent.application.dto.request.ParCategoriaProductoRequest;
import com.agroconecta.estudent.application.dto.response.ParCategoriaProductoResponse;

import java.util.List;

public interface ParCategoriaProductoService {

    ParCategoriaProductoResponse crear(ParCategoriaProductoRequest request);

    List<ParCategoriaProductoResponse> listar();

    ParCategoriaProductoResponse buscarPorId(Integer id);

    ParCategoriaProductoResponse actualizar(Integer id, ParCategoriaProductoRequest request);

    void eliminar(Integer id);
}
