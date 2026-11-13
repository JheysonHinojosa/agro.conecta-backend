package com.agroconecta.estudent.application.services;

import com.agroconecta.estudent.application.dto.request.LoteRequest;
import com.agroconecta.estudent.application.dto.response.LoteResponse;

import java.util.List;

public interface LoteService {

    LoteResponse crear(LoteRequest request);

    List<LoteResponse> listar();

    LoteResponse buscarPorId(Long id);

    LoteResponse actualizar(Long id, LoteRequest request);

    void eliminar(Long id);
}