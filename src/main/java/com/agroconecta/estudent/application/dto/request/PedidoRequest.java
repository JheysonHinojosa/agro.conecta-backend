package com.agroconecta.estudent.application.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PedidoRequest(

        @NotBlank(message = "El código del pedido es requerido")
        @Size(max = 20, message = "El código del pedido admite máximo 20 caracteres")
        String codigoPedido,

        @NotNull(message = "El comprador es requerido")
        Long idComprador,

        @NotNull(message = "El centro de acopio es requerido")
        Long idCentroAcopio,

        @NotNull(message = "La moneda es requerida")
        Integer idMoneda,

        @NotNull(message = "La fecha de entrega estimada es requerida")
        @FutureOrPresent(message = "La fecha de entrega estimada no puede ser pasada")
        LocalDate fechaEntregaEstimada,

        @Size(max = 300, message = "La observación admite máximo 300 caracteres")
        String observacion
) {}