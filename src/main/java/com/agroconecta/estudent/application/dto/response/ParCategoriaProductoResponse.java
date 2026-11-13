package com.agroconecta.estudent.application.dto.response;

public record ParCategoriaProductoResponse(
        Integer idCategoria,
        String codigo,
        String nombre,
        String descripcion,
        boolean activo
) {}