package com.agroconecta.estudent.infrastructure.adapter.out.repository;

import com.agroconecta.estudent.infrastructure.adapter.out.Entity.MovimientoInventarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovimientoInventarioRepository extends JpaRepository<MovimientoInventarioEntity, Long> {

    List<MovimientoInventarioEntity> findByPedidoIdPedidoAndTipoMovimientoCodigo(Long idPedido, String codigo);

    List<MovimientoInventarioEntity> findByPedidoIdPedidoAndObservacionStartingWith(Long idPedido, String prefijo);

    @Query("""
           select m from MovimientoInventarioEntity m
           join fetch m.inventario
           join fetch m.tipoMovimiento
           left join fetch m.pedido
           left join fetch m.lote
           order by m.fechaMovimiento desc
           """)
    List<MovimientoInventarioEntity> findAllConRelaciones();
}