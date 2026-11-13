package com.agroconecta.estudent.application.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CentroAcopioRequest(

        @NotBlank(message = "El código es requerido")
        @Size(max = 20, message = "El código admite máximo 20 caracteres")
        String codigo,

        @NotBlank(message = "El nombre es requerido")
        @Size(max = 120, message = "El nombre admite máximo 120 caracteres")
        String nombre,

        @NotNull(message = "El país es requerido")
        Integer idPais,

        @NotBlank(message = "La ciudad es requerida")
        @Size(max = 80, message = "La ciudad admite máximo 80 caracteres")
        String ciudad,

        @NotBlank(message = "La dirección es requerida")
        @Size(max = 200, message = "La dirección admite máximo 200 caracteres")
        String direccion,

        @NotNull(message = "La latitud es requerida")
        @DecimalMin(value = "-90.0", message = "La latitud mínima es -90")
        @DecimalMax(value = "90.0", message = "La latitud máxima es 90")
        BigDecimal latitud,

        @NotNull(message = "La longitud es requerida")
        @DecimalMin(value = "-180.0", message = "La longitud mínima es -180")
        @DecimalMax(value = "180.0", message = "La longitud máxima es 180")
        BigDecimal longitud,

        @NotNull(message = "La capacidad en kg es requerida")
        @Positive(message = "La capacidad debe ser mayor a cero")
        BigDecimal capacidadKg,

        @NotBlank(message = "El responsable es requerido")
        @Size(max = 120, message = "El responsable admite máximo 120 caracteres")
        String responsable,

        @NotBlank(message = "El teléfono es requerido")
        @Size(max = 20, message = "El teléfono admite máximo 20 caracteres")
        String telefono,

        @NotNull(message = "El estado activo es requerido")
        Boolean activo
) {}