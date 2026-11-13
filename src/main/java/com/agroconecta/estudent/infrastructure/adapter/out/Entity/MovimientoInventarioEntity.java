package com.agroconecta.estudent.infrastructure.adapter.out.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "movimiento_inventario")
@Getter
@Setter
@NoArgsConstructor
public class MovimientoInventarioEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento")
    private Long idMovimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_inventario")
    private InventarioEntity inventario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_movimiento")
    private ParTipoMovimientoEntity tipoMovimiento;

    @Column(name = "cantidad", precision = 14, scale = 2)
    private BigDecimal cantidad;

    @Column(name = "stock_anterior", precision = 14, scale = 2)
    private BigDecimal stockAnterior;

    @Column(name = "stock_posterior", precision = 14, scale = 2)
    private BigDecimal stockPosterior;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido")
    private PedidoEntity pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_lote")
    private LoteEntity lote;

    @Column(name = "fecha_movimiento")
    private OffsetDateTime fechaMovimiento;

    @Column(name = "observacion", length = 300)
    private String observacion;
}