package com.agroconecta.estudent.application.services.impl;

import com.agroconecta.estudent.application.dto.request.ResenaLoteRequest;
import com.agroconecta.estudent.application.dto.request.ResenaLoteUpdateRequest;
import com.agroconecta.estudent.application.dto.response.ResenaLoteResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.mapper.ResenaLoteMapper;
import com.agroconecta.estudent.application.services.ResenaLoteService;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.CompradorEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.LoteEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.PedidoEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ResenaLoteEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.CompradorRepository;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.DetallePedidoRepository;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.LoteRepository;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.PedidoRepository;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.ResenaLoteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ResenaLoteServiceImpl implements ResenaLoteService {

    private final ResenaLoteRepository resenaRepository;
    private final LoteRepository loteRepository;
    private final CompradorRepository compradorRepository;
    private final PedidoRepository pedidoRepository;
    private final DetallePedidoRepository detallePedidoRepository;
    private final ResenaLoteMapper resenaMapper;

    @Override
    public ResenaLoteResponse crear(ResenaLoteRequest request) {

        LoteEntity lote = buscarLote(request.idLote());
        CompradorEntity comprador = buscarComprador(request.idComprador());
        PedidoEntity pedido = buscarPedido(request.idPedido());

        // El pedido debe ser del comprador
        if (!pedido.getComprador().getIdComprador().equals(comprador.getIdComprador())) {
            throw new IllegalArgumentException("El pedido no pertenece al comprador indicado");
        }
        // El pedido debe contener el lote que se reseña
        if (!detallePedidoRepository.existsByPedidoIdPedidoAndLoteIdLote(
                pedido.getIdPedido(), lote.getIdLote())) {
            throw new IllegalArgumentException("El pedido no incluye el lote que se quiere reseñar");
        }
        // Una sola reseña por lote, comprador y pedido
        if (resenaRepository.existsByLoteIdLoteAndCompradorIdCompradorAndPedidoIdPedido(
                lote.getIdLote(), comprador.getIdComprador(), pedido.getIdPedido())) {
            throw new IllegalArgumentException("Ya existe una reseña de este comprador para ese lote y pedido");
        }

        ResenaLoteEntity entity = resenaMapper.toEntity(request);
        entity.setLote(lote);
        entity.setComprador(comprador);
        entity.setPedido(pedido);
        entity.setFechaResena(OffsetDateTime.now());

        return resenaMapper.toResponse(resenaRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResenaLoteResponse> listar() {
        return resenaRepository.findAllConRelaciones()
                .stream()
                .map(resenaMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ResenaLoteResponse buscarPorId(Long id) {
        return resenaMapper.toResponse(buscarResena(id));
    }

    @Override
    public ResenaLoteResponse actualizar(Long id, ResenaLoteUpdateRequest request) {

        ResenaLoteEntity entity = buscarResena(id);
        resenaMapper.updateEntity(entity, request);

        return resenaMapper.toResponse(resenaRepository.save(entity));
    }

    @Override
    public void eliminar(Long id) {
        // La tabla resena_lote no tiene columna "activo", por eso el borrado es físico
        resenaRepository.delete(buscarResena(id));
    }

    // ---------- métodos privados ----------

    private ResenaLoteEntity buscarResena(Long id) {
        return resenaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reseña de lote no encontrada"));
    }

    private LoteEntity buscarLote(Long id) {
        return loteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lote no encontrado"));
    }

    private CompradorEntity buscarComprador(Long id) {
        return compradorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comprador no encontrado"));
    }

    private PedidoEntity buscarPedido(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado"));
    }
}