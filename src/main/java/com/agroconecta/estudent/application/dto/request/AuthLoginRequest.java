package com.agroconecta.estudent.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequest(
        @NotBlank(message = "El username es requerido")
        String username,

        @NotBlank(message = "La contraseña es requerida")
        String password
) {}
