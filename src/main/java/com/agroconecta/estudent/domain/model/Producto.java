package com.agroconecta.estudent.domain.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Producto extends BaseModel {

     Long idProducto;
     String codigo;
     String nombre;
     String descripcion;
     Integer idCategoria;
     Integer idUnidadMedida;
     BigDecimal precioReferencial;
     Integer idMoneda;
     Long idProductor;

    @Builder.Default
     boolean activo = true;
}