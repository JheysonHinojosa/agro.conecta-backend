package com.agroconecta.estudent.infrastructure.adapter.out.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "historial_estado_pedido")
@Getter
@Setter
@NoArgsConstructor
public class HistorialEstadoPedidoEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial")
    private Long idHistorial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido")
    private PedidoEntity pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado_anterior")
    private ParEstadoPedidoEntity estadoAnterior;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado_nuevo")
    private ParEstadoPedidoEntity estadoNuevo;

    @Column(name = "fecha_cambio")
    private OffsetDateTime fechaCambio;

    @Column(name = "comentario", length = 300)
    private String comentario;
}