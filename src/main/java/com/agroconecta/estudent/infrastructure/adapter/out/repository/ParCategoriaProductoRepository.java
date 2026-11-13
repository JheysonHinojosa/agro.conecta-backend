package com.agroconecta.estudent.infrastructure.adapter.out.repository;

import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParCategoriaProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParCategoriaProductoRepository extends JpaRepository<ParCategoriaProductoEntity, Integer> {

    boolean existsByCodigo(String codigo);

    boolean existsByCodigoAndIdCategoriaNot(String codigo, Integer idCategoria);
}