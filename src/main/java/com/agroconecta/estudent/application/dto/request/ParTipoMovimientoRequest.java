package com.agroconecta.estudent.application.dto.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ParTipoMovimientoRequest(

        @NotBlank(message = "El código es requerido")
        @Size(max = 30, message = "El código admite máximo 30 caracteres")
        String codigo,

        @NotBlank(message = "El nombre es requerido")
        @Size(max = 60, message = "El nombre admite máximo 60 caracteres")
        String nombre,

        @NotNull(message = "El signo es requerido")
        Short signo,

        @NotNull(message = "El estado activo es requerido")
        Boolean activo
) {

        @AssertTrue(message = "El signo debe ser 1 (entrada) o -1 (salida)")
        public boolean isSignoValido() {
                return signo == null || signo == 1 || signo == -1;
        }
}