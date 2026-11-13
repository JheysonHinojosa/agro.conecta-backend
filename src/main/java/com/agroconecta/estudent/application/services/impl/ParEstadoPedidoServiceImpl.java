package com.agroconecta.estudent.application.services.impl;

import com.agroconecta.estudent.application.dto.request.ParEstadoPedidoRequest;
import com.agroconecta.estudent.application.dto.response.ParEstadoPedidoResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.mapper.ParEstadoPedidoMapper;
import com.agroconecta.estudent.application.services.ParEstadoPedidoService;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParEstadoPedidoEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.ParEstadoPedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ParEstadoPedidoServiceImpl implements ParEstadoPedidoService {

    private final ParEstadoPedidoRepository estadoPedidoRepository;
    private final ParEstadoPedidoMapper estadoPedidoMapper;

    @Override
    public ParEstadoPedidoResponse crear(ParEstadoPedidoRequest request) {

        if (estadoPedidoRepository.existsByCodigo(request.codigo())) {
            throw new IllegalArgumentException("El código de estado de pedido ya está en uso");
        }

        ParEstadoPedidoEntity entity = estadoPedidoMapper.toEntity(request);

        return estadoPedidoMapper.toResponse(estadoPedidoRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParEstadoPedidoResponse> listar() {
        return estadoPedidoRepository.findAll()
                .stream()
                .map(estadoPedidoMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ParEstadoPedidoResponse buscarPorId(Integer id) {
        return estadoPedidoMapper.toResponse(buscarEstado(id));
    }

    @Override
    public ParEstadoPedidoResponse actualizar(Integer id, ParEstadoPedidoRequest request) {

        ParEstadoPedidoEntity entity = buscarEstado(id);

        if (estadoPedidoRepository.existsByCodigoAndIdEstadoPedidoNot(request.codigo(), id)) {
            throw new IllegalArgumentException("El código de estado de pedido ya está en uso");
        }

        estadoPedidoMapper.updateEntity(entity, request);

        return estadoPedidoMapper.toResponse(estadoPedidoRepository.save(entity));
    }

    @Override
    public void eliminar(Integer id) {
        // Eliminación lógica: el estado está referenciado por pedidos e historial
        ParEstadoPedidoEntity entity = buscarEstado(id);
        entity.setActivo(false);
        estadoPedidoRepository.save(entity);
    }

    // ---------- métodos privados ----------

    private ParEstadoPedidoEntity buscarEstado(Integer id) {
        return estadoPedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estado de pedido no encontrado"));
    }
}