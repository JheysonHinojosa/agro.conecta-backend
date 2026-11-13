package com.agroconecta.estudent.infrastructure.adapter.out.repository;

import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParTipoAlertaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParTipoAlertaRepository extends JpaRepository<ParTipoAlertaEntity, Integer> {

    boolean existsByCodigo(String codigo);

    boolean existsByCodigoAndIdTipoAlertaNot(String codigo, Integer idTipoAlerta);
}