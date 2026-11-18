package com.agroconecta.estudent.infrastructure.adapter.out.repository;

import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParTipoMovimientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface ParTipoMovimientoRepository extends JpaRepository<ParTipoMovimientoEntity, Integer> {

    boolean existsByCodigo(String codigo);

    boolean existsByCodigoAndIdTipoMovimientoNot(String codigo, Integer idTipoMovimiento);

    Optional<ParTipoMovimientoEntity> findByCodigo(String codigo);

    List<ParTipoMovimientoEntity> findAllByActivoTrueAndSigno(Short signo);
}