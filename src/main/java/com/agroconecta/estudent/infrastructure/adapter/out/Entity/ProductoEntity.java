package com.agroconecta.estudent.infrastructure.adapter.out.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "producto")
@Getter
@Setter
@NoArgsConstructor
public class ProductoEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;

    @Column(name = "codigo", length = 30)
    private String codigo;

    @Column(name = "nombre", length = 150)
    private String nombre;

    @Column(name = "descripcion", length = 300)
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria")
    private ParCategoriaProductoEntity categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_unidad_medida")
    private ParUnidadMedidaEntity unidadMedida;

    @Column(name = "precio_referencial", precision = 12, scale = 2)
    private BigDecimal precioReferencial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_moneda")
    private ParMonedaEntity moneda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_productor")
    private ProductorEntity productor;

    @Column(name = "activo")
    private boolean activo;
}