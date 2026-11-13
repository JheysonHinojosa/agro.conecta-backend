package com.agroconecta.estudent.infrastructure.adapter.out.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "par_estado_pedido")
@Getter
@Setter
@NoArgsConstructor
public class ParEstadoPedidoEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_pedido")
    private Integer idEstadoPedido;

    @Column(name = "codigo", length = 20)
    private String codigo;

    @Column(name = "nombre", length = 40)
    private String nombre;

    @Column(name = "orden")
    private Short orden;

    @Column(name = "es_final")
    private boolean esFinal;

    @Column(name = "activo")
    private boolean activo;
}