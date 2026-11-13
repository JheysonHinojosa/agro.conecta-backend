package com.agroconecta.estudent.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRequest(

        @NotNull(message = "El rol es requerido")
        Integer idRol,

        @NotBlank(message = "El username es requerido")
        @Size(max = 50, message = "El username admite máximo 50 caracteres")
        String username,

        @NotBlank(message = "El email es requerido")
        @Email(message = "El email no es válido")
        @Size(max = 120, message = "El email admite máximo 120 caracteres")
        String email,

        @Size(min = 4, max = 100, message = "La contraseña debe tener entre 4 y 100 caracteres")
        String passwordHash,

        @NotBlank(message = "El nombre es requerido")
        @Size(max = 80, message = "Los nombres admiten máximo 80 caracteres")
        String nombres,

        @NotBlank(message = "El apellido es requerido")
        @Size(max = 80, message = "Los apellidos admiten máximo 80 caracteres")
        String apellidos,

        @NotNull(message = "El comprador es requerido")
        Long idComprador,

        @NotNull(message = "El estado activo es requerido")
        Boolean activo
) {}