package com.agroconecta.estudent.domain.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ParPais extends BaseModel {

    private Integer idMoneda;
    private String codigo;
    private String nombre;
    private String simbolo;

    @Builder.Default
    private boolean activo = true;

}
