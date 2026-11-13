package com.agroconecta.estudent.domain.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CentroAcopio extends BaseModel {

     long idCentroAcopio;
     String codigo;
     String nombre;
     Integer idPais;
     String ciudad;
     String direccion;
     BigDecimal latitud;
     BigDecimal longitud;
     BigDecimal capacidadKg;
     String responsable;
     String telefono;

    @Builder.Default
    private boolean activo = true;
}
