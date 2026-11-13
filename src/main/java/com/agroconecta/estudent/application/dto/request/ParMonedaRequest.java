package com.agroconecta.estudent.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ParMonedaRequest(

        @NotBlank(message = "El código es requerido")
        @Pattern(regexp = "^[A-Za-z]{3}$", message = "El código debe tener exactamente 3 letras (ej. PEN, USD)")
        String codigo,

        @NotBlank(message = "El nombre es requerido")
        @Size(max = 60, message = "El nombre admite máximo 60 caracteres")
        String nombre,

        @NotBlank(message = "El símbolo es requerido")
        @Size(max = 5, message = "El símbolo admite máximo 5 caracteres")
        String simbolo,

        @NotNull(message = "El estado activo es requerido")
        Boolean activo
) {}