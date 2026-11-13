package com.agroconecta.estudent.application.services;

import com.agroconecta.estudent.application.dto.request.ParTipoCompradorRequest;
import com.agroconecta.estudent.application.dto.response.ParTipoCompradorResponse;

import java.util.List;

public interface ParTipoCompradorService {

    ParTipoCompradorResponse crear(ParTipoCompradorRequest request);

    List<ParTipoCompradorResponse> listar();

    ParTipoCompradorResponse buscarPorId(Integer id);

    ParTipoCompradorResponse actualizar(Integer id, ParTipoCompradorRequest request);

    void eliminar(Integer id);
}
