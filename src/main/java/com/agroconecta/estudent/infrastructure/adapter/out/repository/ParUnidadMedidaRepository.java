package com.agroconecta.estudent.infrastructure.adapter.out.repository;

import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParUnidadMedidaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParUnidadMedidaRepository extends JpaRepository<ParUnidadMedidaEntity, Integer> {

    boolean existsByCodigo(String codigo);

    boolean existsByCodigoAndIdUnidadMedidaNot(String codigo, Integer idUnidadMedida);
}
