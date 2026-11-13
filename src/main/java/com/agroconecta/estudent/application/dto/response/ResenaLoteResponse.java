package com.agroconecta.estudent.application.dto.response;

import java.time.OffsetDateTime;

public record ResenaLoteResponse(
        Long idResena,
        Long idLote,
        String codigoLote,
        Long idComprador,
        String nombreComprador,
        Long idPedido,
        String codigoPedido,
        Short puntuacion,
        String comentario,
        OffsetDateTime fechaResena
) {}