package com.agroconecta.estudent.application.services;

import com.agroconecta.estudent.application.dto.request.CompradorRequest;
import com.agroconecta.estudent.application.dto.response.CompradorResponse;

import java.util.List;

public interface CompradorService {

    CompradorResponse crear(CompradorRequest request);

    List<CompradorResponse> listar();

    CompradorResponse buscarPorId(Long id);

    CompradorResponse actualizar(Long id, CompradorRequest request);

    void eliminar(Long id);
}