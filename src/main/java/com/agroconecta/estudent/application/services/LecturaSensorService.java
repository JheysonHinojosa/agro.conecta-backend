package com.agroconecta.estudent.application.services;

import com.agroconecta.estudent.application.dto.request.LecturaSensorRequest;
import com.agroconecta.estudent.application.dto.response.LecturaSensorResponse;

import java.util.List;

public interface LecturaSensorService {

    LecturaSensorResponse crear(LecturaSensorRequest request);

    List<LecturaSensorResponse> listar();

    LecturaSensorResponse buscarPorId(Long id);

    LecturaSensorResponse actualizar(Long id, LecturaSensorRequest request);

    void eliminar(Long id);
}