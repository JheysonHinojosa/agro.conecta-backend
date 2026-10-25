package com.agroconecta.estudent.application.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AlertaResponse(
        long idAlerta,
        Integer idTipoAlerta,
        long idCentroAcopio,
        long idProductor,
        long idSensor,
        long idLector,
        String mensaje,
        BigDecimal valorMedido,
        BigDecimal valorUmbral,
        LocalDateTime fechaAlerta,
        LocalDateTime fechaAtencion,
        String AtendidaPor,
        boolean atendida
) {}
