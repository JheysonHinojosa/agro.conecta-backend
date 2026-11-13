package com.agroconecta.estudent.application.services;

import com.agroconecta.estudent.application.dto.request.ResenaLoteRequest;
import com.agroconecta.estudent.application.dto.request.ResenaLoteUpdateRequest;
import com.agroconecta.estudent.application.dto.response.ResenaLoteResponse;

import java.util.List;

public interface ResenaLoteService {

    ResenaLoteResponse crear(ResenaLoteRequest request);

    List<ResenaLoteResponse> listar();

    ResenaLoteResponse buscarPorId(Long id);

    ResenaLoteResponse actualizar(Long id, ResenaLoteUpdateRequest request);

    void eliminar(Long id);
}