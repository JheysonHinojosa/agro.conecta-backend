package com.agroconecta.estudent.application.dto.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record AlertaResponse(
        Long idAlerta,
        Integer idTipoAlerta,
        String nombreTipoAlerta,
        Long idCentroAcopio,
        Long idProducto,
        Long idSensor,
        Long idLectura,
        String mensaje,
        BigDecimal valorMedido,
        BigDecimal valorUmbral,
        OffsetDateTime fechaAlerta,
        boolean atendida,
        OffsetDateTime fechaAtencion,
        String atendidaPor
) {}