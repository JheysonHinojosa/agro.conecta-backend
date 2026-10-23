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

    private Long idProducto;
    private String codigo;
    private String nombre;
    private String descripcion;
    private Integer idCategoria;
    private Integer idUnidadMedida;
    private BigDecimal precioReferencial;
    private Integer idMoneda;
    private Long idProductor;

    @Builder.Default
    private boolean activo = true;
}