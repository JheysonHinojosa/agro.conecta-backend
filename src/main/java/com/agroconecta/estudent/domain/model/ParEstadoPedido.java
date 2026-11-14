package com.agroconecta.estudent.domain.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ParEstadoPedido extends BaseModel {

    Integer idEstadoPedido;
    String codigo;
    String nombre;
    String orden;

    @Builder.Default
    boolean esFinal = true;

    @Builder.Default
    boolean activo = true;
}
