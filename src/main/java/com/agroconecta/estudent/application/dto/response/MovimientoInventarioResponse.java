package com.agroconecta.estudent.application.dto.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record MovimientoInventarioResponse(
        Long idMovimiento,
        Long idInventario,
        Integer idTipoMovimiento,
        String nombreTipoMovimiento,
        BigDecimal cantidad,
        BigDecimal stockAnterior,
        BigDecimal stockPosterior,
        Long idPedido,
        Long idLote,
        String codigoLote,
        OffsetDateTime fechaMovimiento,
        String observacion
) {}