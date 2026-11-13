package com.agroconecta.estudent.infrastructure.adapter.out.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "inventario")
@Getter
@Setter
@NoArgsConstructor
public class InventarioEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventario")
    private Long idInventario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_centro_acopio")
    private CentroAcopioEntity centroAcopio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto")
    private ProductoEntity producto;

    @Column(name = "stock_actual", precision = 14, scale = 2)
    private BigDecimal stockActual;

    @Column(name = "stock_minimo", precision = 14, scale = 2)
    private BigDecimal stockMinimo;
}