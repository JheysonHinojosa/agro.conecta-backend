package com.agroconecta.estudent.application.services;

import com.agroconecta.estudent.application.dto.request.PedidoOperacionEstadoRequest;
import com.agroconecta.estudent.application.dto.request.PedidoOperacionRequest;
import com.agroconecta.estudent.application.dto.response.PedidoOperacionResponse;

public interface PedidoOperacionService {

    PedidoOperacionResponse crear(PedidoOperacionRequest request);

    PedidoOperacionResponse cambiarEstado(Long idPedido, PedidoOperacionEstadoRequest request);
}
