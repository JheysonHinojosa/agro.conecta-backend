package com.agroconecta.estudent.application.dto.response;

import java.math.BigDecimal;

public record ProductoResponse(
        Long idProducto,
        String codigo,
        String nombre,
        String descripcion,
        Integer idCategoria,
        String nombreCategoria,
        Integer idUnidadMedida,
        String codigoUnidadMedida,
        BigDecimal precioReferencial,
        Integer idMoneda,
        String codigoMoneda,
        Long idProductor,
        String nombreProductor,
        boolean activo
) {}