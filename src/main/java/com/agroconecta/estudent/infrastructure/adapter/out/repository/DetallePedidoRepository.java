package com.agroconecta.estudent.infrastructure.adapter.out.repository;

import com.agroconecta.estudent.infrastructure.adapter.out.Entity.DetallePedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface DetallePedidoRepository extends JpaRepository<DetallePedidoEntity, Long> {

    boolean existsByPedidoIdPedido(Long idPedido);

    @Query("select coalesce(sum(d.subtotal), 0) from DetallePedidoEntity d where d.pedido.idPedido = :idPedido")
    BigDecimal sumarSubtotales(@Param("idPedido") Long idPedido);

    boolean existsByPedidoIdPedidoAndLoteIdLote(Long idPedido, Long idLote);
}