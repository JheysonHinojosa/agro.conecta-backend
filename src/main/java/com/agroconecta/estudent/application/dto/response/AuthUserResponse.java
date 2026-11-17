package com.agroconecta.estudent.application.dto.response;

public record AuthUserResponse(
        Long id_usuario,
        String username,
        String rol
) {}
