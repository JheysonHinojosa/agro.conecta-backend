package com.agroconecta.estudent.application.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

public record PedidoResponse(
        Long idPedido,
        String codigoPedido,
        Long idComprador,
        String nombreComprador,
        Long idCentroAcopio,
        String nombreCentroAcopio,
        Integer idEstadoPedido,
        String nombreEstadoPedido,
        boolean estadoFinal,
        Integer idMoneda,
        String codigoMoneda,
        OffsetDateTime fechaPedido,
        LocalDate fechaEntregaEstimada,
        BigDecimal montoTotal,
        String observacion
) {}