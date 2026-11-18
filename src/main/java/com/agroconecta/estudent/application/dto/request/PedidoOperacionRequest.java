package com.agroconecta.estudent.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record PedidoOperacionRequest(

        @NotNull(message = "El comprador es requerido")
        @Positive(message = "El comprador debe ser válido")
        Long idComprador,

        @NotNull(message = "El centro de acopio es requerido")
        @Positive(message = "El centro de acopio debe ser válido")
        Long idCentroAcopio,

        @NotEmpty(message = "El pedido debe incluir al menos un detalle")
        List<@NotNull @Valid PedidoOperacionDetalleRequest> detalle,

        @FutureOrPresent(message = "La fecha de entrega estimada no puede ser pasada")
        LocalDate fechaEntregaEstimada,

        @Size(max = 300, message = "La observación admite máximo 300 caracteres")
        String observacion
) {}
