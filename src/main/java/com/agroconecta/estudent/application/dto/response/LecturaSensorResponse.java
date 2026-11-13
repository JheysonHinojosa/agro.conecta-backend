package com.agroconecta.estudent.application.dto.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record LecturaSensorResponse(
        Long idLectura,
        Long idSensor,
        String codigoSensor,
        OffsetDateTime fechaLectura,
        BigDecimal temperaturaC,
        BigDecimal humedadPct
) {}