package com.agroconecta.estudent.infrastructure.adapter.out.repository;

import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {

    boolean existsByCodigo(String codigo);

    boolean existsByCodigoAndIdProductoNot(String codigo, Long idProducto);

    @Query("""
           select p from ProductoEntity p
           join fetch p.categoria
           join fetch p.unidadMedida
           join fetch p.moneda
           join fetch p.productor
           order by p.nombre
           """)
    List<ProductoEntity> findAllConRelaciones();
}