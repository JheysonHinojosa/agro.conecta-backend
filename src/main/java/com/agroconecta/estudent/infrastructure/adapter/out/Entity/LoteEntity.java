package com.agroconecta.estudent.infrastructure.adapter.out.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "lote")
@Getter
@Setter
@NoArgsConstructor
public class LoteEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lote")
    private Long idLote;

    @Column(name = "codigo_lote", length = 30)
    private String codigoLote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto")
    private ProductoEntity producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_centro_acopio")
    private CentroAcopioEntity centroAcopio;

    @Column(name = "fecha_cosecha")
    private LocalDate fechaCosecha;

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @Column(name = "cantidad_ingresada", precision = 14, scale = 2)
    private BigDecimal cantidadIngresada;

    @Column(name = "observacion", length = 300)
    private String observacion;

    @Column(name = "activo")
    private boolean activo;
}