package com.agroconecta.estudent.application.dto.response;

public record ParRolResponse(
        Integer idRol,
        String codigo,
        String nombre,
        String descripcion,
        boolean activo
) {
}
