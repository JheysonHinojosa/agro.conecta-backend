package com.agroconecta.estudent.application.dto.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public record SensorHistorialResponse(List<Item> items, long total) {

    public record Item(
            String codigo_sensor,
            OffsetDateTime fecha_lectura,
            BigDecimal temperatura_c,
            BigDecimal humedad_pct
    ) {}
}
