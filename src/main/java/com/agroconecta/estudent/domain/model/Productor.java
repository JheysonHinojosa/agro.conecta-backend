package com.agroconecta.estudent.domain.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Productor extends BaseModel {

    Long idProductor;
    Long idCentroAcopio;
     Integer idTipoDocumento;
     Integer idPais;
     Integer idCategoriaPrincipal;
     String nroDocumento;
     String nombreRazonSocial;
     String email;
     String telefono;
     String direccion;
     LocalDate fechaRegistro;

    @Builder.Default
     boolean activo = true;
}