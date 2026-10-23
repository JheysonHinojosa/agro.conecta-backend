package com.agroconecta.estudent.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Inventario extends BaseModel {

    private long idInventario;
    private long idCentroAcopio;
    private long idProducto;
    private int stockActual;
    private int stockMinimo;
}
