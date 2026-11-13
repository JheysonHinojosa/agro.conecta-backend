package com.agroconecta.estudent.application.dto.response;

import java.time.OffsetDateTime;

public record UsuarioResponse(
        Long idUsuario,
        Integer idRol,
        String nombreRol,
        String username,
        String email,
        String nombres,
        String apellidos,
        Long idComprador,
        OffsetDateTime ultimoAcceso,
        boolean activo
) {}