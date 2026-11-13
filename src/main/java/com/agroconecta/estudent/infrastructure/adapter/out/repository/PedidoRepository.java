package com.agroconecta.estudent.infrastructure.adapter.out.repository;

import com.agroconecta.estudent.infrastructure.adapter.out.Entity.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {

    boolean existsByCodigoPedido(String codigoPedido);

    @Query("""
           select p from PedidoEntity p
           join fetch p.comprador
           join fetch p.centroAcopio
           join fetch p.estadoPedido
           join fetch p.moneda
           order by p.fechaPedido desc
           """)
    List<PedidoEntity> findAllConRelaciones();
}