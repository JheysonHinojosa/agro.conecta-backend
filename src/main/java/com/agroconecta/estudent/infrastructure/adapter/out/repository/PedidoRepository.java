package com.agroconecta.estudent.infrastructure.adapter.out.repository;

import com.agroconecta.estudent.infrastructure.adapter.out.Entity.PedidoEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {

    boolean existsByCodigoPedido(String codigoPedido);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from PedidoEntity p where p.idPedido = :id")
    Optional<PedidoEntity> findByIdForUpdate(@Param("id") Long id);

    @Query(value = "SELECT nextval('seq_codigo_pedido')", nativeQuery = true)
    Long getNextCodigoPedidoSequence();

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