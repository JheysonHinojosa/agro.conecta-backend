package com.agroconecta.estudent.domain.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ParUnidadMedida extends BaseModel {

     Integer idUnidadMedida;
     String codigo;
     String nombre;

    @Builder.Default
     boolean activo = true;
}
