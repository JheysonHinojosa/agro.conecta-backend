package com.agroconecta.estudent.infrastructure.adapter.out.repository;

import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParEstadoPedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParEstadoPedidoRepository extends JpaRepository<ParEstadoPedidoEntity, Integer> {

    boolean existsByCodigo(String codigo);

    boolean existsByCodigoAndIdEstadoPedidoNot(String codigo, Integer idEstadoPedido);

    Optional<ParEstadoPedidoEntity> findFirstByActivoTrueOrderByOrdenAsc();
}