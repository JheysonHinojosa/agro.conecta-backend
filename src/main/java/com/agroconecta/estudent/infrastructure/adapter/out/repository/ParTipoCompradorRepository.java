package com.agroconecta.estudent.infrastructure.adapter.out.repository;

import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParTipoCompradorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParTipoCompradorRepository extends JpaRepository<ParTipoCompradorEntity, Integer> {

    boolean existsByCodigo(String codigo);

    boolean existsByCodigoAndIdTipoCompradorNot(String codigo, Integer idTipoComprador);
}