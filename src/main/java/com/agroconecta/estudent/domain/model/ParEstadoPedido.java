package com.agroconecta.estudent.domain.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

public class ParEstadoPedido extends BaseModel {
    private Integer idEstadoPedido;
    private String codigo;
    private String nombre;
    private String orden;

    @Builder.Default
    private boolean esFinal = true;
    private boolean activo = true;
}
