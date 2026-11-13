package com.agroconecta.estudent.application.dto.response;

import java.math.BigDecimal;

public record InventarioResponse(
        Long idInventario,
        Long idCentroAcopio,
        String nombreCentroAcopio,
        Long idProducto,
        String nombreProducto,
        BigDecimal stockActual,
        BigDecimal stockMinimo,
        boolean bajoMinimo
) {}