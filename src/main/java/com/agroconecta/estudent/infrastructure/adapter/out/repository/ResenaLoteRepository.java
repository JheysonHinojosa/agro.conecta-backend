package com.agroconecta.estudent.infrastructure.adapter.out.repository;

import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ResenaLoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResenaLoteRepository extends JpaRepository<ResenaLoteEntity, Long> {

    boolean existsByLoteIdLoteAndCompradorIdCompradorAndPedidoIdPedido(
            Long idLote, Long idComprador, Long idPedido);

    @Query("""
           select r from ResenaLoteEntity r
           join fetch r.lote
           join fetch r.comprador
           join fetch r.pedido
           order by r.fechaResena desc
           """)
    List<ResenaLoteEntity> findAllConRelaciones();
}