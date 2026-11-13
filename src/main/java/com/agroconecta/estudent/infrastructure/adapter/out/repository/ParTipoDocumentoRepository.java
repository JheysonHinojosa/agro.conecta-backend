package com.agroconecta.estudent.infrastructure.adapter.out.repository;

import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParTipoDocumentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParTipoDocumentoRepository extends JpaRepository<ParTipoDocumentoEntity, Integer> {

    boolean existsByCodigo(String codigo);

    boolean existsByCodigoAndIdTipoDocumentoNot(String codigo, Integer idTipoDocumento);
}