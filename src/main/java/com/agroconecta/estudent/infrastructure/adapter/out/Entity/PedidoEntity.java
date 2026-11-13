package com.agroconecta.estudent.infrastructure.adapter.out.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "pedido")
@Getter
@Setter
@NoArgsConstructor
public class PedidoEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long idPedido;

    @Column(name = "codigo_pedido", length = 20)
    private String codigoPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_comprador")
    private CompradorEntity comprador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_centro_acopio")
    private CentroAcopioEntity centroAcopio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado_pedido")
    private ParEstadoPedidoEntity estadoPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_moneda")
    private ParMonedaEntity moneda;

    @Column(name = "fecha_pedido")
    private OffsetDateTime fechaPedido;

    @Column(name = "fecha_entrega_estimada")
    private LocalDate fechaEntregaEstimada;

    @Column(name = "monto_total", precision = 14, scale = 2)
    private BigDecimal montoTotal;

    @Column(name = "observacion", length = 300)
    private String observacion;
}