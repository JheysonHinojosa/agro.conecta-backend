package com.agroconecta.estudent.infrastructure.adapter.out.repository;

import com.agroconecta.estudent.infrastructure.adapter.out.Entity.HistorialEstadoPedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HistorialEstadoPedidoRepository extends JpaRepository<HistorialEstadoPedidoEntity, Long> {

    @Query("""
           select h from HistorialEstadoPedidoEntity h
           join fetch h.pedido
           join fetch h.estadoNuevo
           left join fetch h.estadoAnterior
           order by h.fechaCambio asc
           """)
    List<HistorialEstadoPedidoEntity> findAllConRelaciones();
}