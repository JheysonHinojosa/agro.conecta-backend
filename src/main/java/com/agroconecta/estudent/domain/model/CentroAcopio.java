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

    private long idCentroAcopio;
    private String codigo;
    private String nombre;
    private Integer idPais;
    private String ciudad;
    private String direccion;
    private BigDecimal latitud;
    private BigDecimal longitud;
    private BigDecimal capacidadKg;
    private String responsable;
    private String telefono;

    @Builder.Default
    private boolean activo = true;
}
