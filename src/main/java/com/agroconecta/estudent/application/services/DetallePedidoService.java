package com.agroconecta.estudent.application.services;

import com.agroconecta.estudent.application.dto.request.DetallePedidoRequest;
import com.agroconecta.estudent.application.dto.response.DetallePedidoResponse;

import java.util.List;

public interface DetallePedidoService {

    DetallePedidoResponse crear(DetallePedidoRequest request);

    List<DetallePedidoResponse> listar();

    DetallePedidoResponse buscarPorId(Long id);

    DetallePedidoResponse actualizar(Long id, DetallePedidoRequest request);

    void eliminar(Long id);
}