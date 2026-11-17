package com.agroconecta.estudent.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record StockMinimoRequest(
        @NotNull(message = "El stock mínimo es requerido")
        @PositiveOrZero(message = "El stock mínimo debe ser mayor o igual a 0")
        @Digits(integer = 12, fraction = 2, message = "El stock mínimo admite hasta 12 enteros y 2 decimales")
        @JsonProperty("stock_minimo")
        BigDecimal stockMinimo
) {}
