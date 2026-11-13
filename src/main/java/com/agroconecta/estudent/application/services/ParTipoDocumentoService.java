package com.agroconecta.estudent.application.services;

import com.agroconecta.estudent.application.dto.request.ParTipoDocumentoRequest;
import com.agroconecta.estudent.application.dto.response.ParTipoDocumentoResponse;

import java.util.List;

public interface ParTipoDocumentoService {

    ParTipoDocumentoResponse crear(ParTipoDocumentoRequest request);

    List<ParTipoDocumentoResponse> listar();

    ParTipoDocumentoResponse buscarPorId(Integer id);

    ParTipoDocumentoResponse actualizar(Integer id, ParTipoDocumentoRequest request);

    void eliminar(Integer id);
}
