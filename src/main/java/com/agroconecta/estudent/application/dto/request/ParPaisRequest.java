package com.agroconecta.estudent.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ParPaisRequest(

        @NotBlank(message = "El código ISO es requerido")
        @Pattern(regexp = "^[A-Za-z]{2}$", message = "El código ISO debe tener exactamente 2 letras (ej. PE, US)")
        String codigoIso,

        @NotBlank(message = "El nombre es requerido")
        @Size(max = 60, message = "El nombre admite máximo 60 caracteres")
        String nombre,

        @NotBlank(message = "El código telefónico es requerido")
        @Size(max = 5, message = "El código telefónico admite máximo 5 caracteres")
        String codigoTelefono,

        @NotNull(message = "La moneda es requerida")
        Integer idMoneda,

        @NotNull(message = "El estado activo es requerido")
        Boolean activo
) {}