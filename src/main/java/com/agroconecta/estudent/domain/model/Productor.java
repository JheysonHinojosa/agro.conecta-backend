package com.agroconecta.estudent.domain.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Productor extends  BaseModel{

    Long idProductor;
    long idCentroAcopio;
    Integer idTipoDocumento;
    Integer idPais;
    Integer idCategoriaPrincipal;
    String nroDocumento;
    String nombreRazonSocial;
    String email;
    String telefono;
    String direccion;
    LocalDate fechaRegistro;
    Boolean activo;
}
