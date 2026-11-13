package com.agroconecta.estudent.domain.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ParMoneda extends BaseModel {

     Integer idMoneda;
     String codigo;
     String nombre;
     String simbolo;

    @Builder.Default
     boolean activo = true;

}
