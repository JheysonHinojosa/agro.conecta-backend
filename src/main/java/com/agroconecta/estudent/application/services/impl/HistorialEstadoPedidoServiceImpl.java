package com.agroconecta.estudent.application.services.impl;

import com.agroconecta.estudent.application.dto.request.HistorialEstadoPedidoRequest;
import com.agroconecta.estudent.application.dto.response.HistorialEstadoPedidoResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.mapper.HistorialEstadoPedidoMapper;
import com.agroconecta.estudent.application.services.HistorialEstadoPedidoService;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.HistorialEstadoPedidoEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParEstadoPedidoEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.PedidoEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.HistorialEstadoPedidoRepository;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.ParEstadoPedidoRepository;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HistorialEstadoPedidoServiceImpl implements HistorialEstadoPedidoService {

    private final HistorialEstadoPedidoRepository historialRepository;
    private final PedidoRepository pedidoRepository;
    private final ParEstadoPedidoRepository parEstadoPedidoRepository;
    private final HistorialEstadoPedidoMapper historialMapper;

    @Override
    public HistorialEstadoPedidoResponse crear(HistorialEstadoPedidoRequest request) {

        validarEstados(request);

        HistorialEstadoPedidoEntity entity = historialMapper.toEntity(request);
        asignarRelaciones(entity, request);

        if (entity.getFechaCambio() == null) {
            entity.setFechaCambio(OffsetDateTime.now());
        }

        return historialMapper.toResponse(historialRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistorialEstadoPedidoResponse> listar() {
        return historialRepository.findAll()
                .stream()
                .map(historialMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HistorialEstadoPedidoResponse buscarPorId(Long id) {
        return historialMapper.toResponse(buscarHistorial(id));
    }

    @Override
    public HistorialEstadoPedidoResponse actualizar(Long id, HistorialEstadoPedidoRequest request) {

        validarEstados(request);

        HistorialEstadoPedidoEntity entity = buscarHistorial(id);

        historialMapper.updateEntity(entity, request);
        asignarRelaciones(entity, request);

        if (entity.getFechaCambio() == null) {
            entity.setFechaCambio(OffsetDateTime.now());
        }

        return historialMapper.toResponse(historialRepository.save(entity));
    }

    @Override
    public void eliminar(Long id) {
        // La tabla no tiene columna "activo", por eso el borrado es físico
        historialRepository.delete(buscarHistorial(id));
    }

    // ---------- métodos privados ----------

    private void validarEstados(HistorialEstadoPedidoRequest request) {
        if (request.idEstadoAnterior() != null
                && request.idEstadoAnterior().equals(request.idEstadoNuevo())) {
            throw new IllegalArgumentException("El estado nuevo debe ser distinto del estado anterior");
        }
    }

    private void asignarRelaciones(HistorialEstadoPedidoEntity entity, HistorialEstadoPedidoRequest request) {
        entity.setPedido(buscarPedido(request.idPedido()));
        entity.setEstadoAnterior(buscarEstadoOpcional(request.idEstadoAnterior()));
        entity.setEstadoNuevo(buscarEstado(request.idEstadoNuevo()));
    }

    private HistorialEstadoPedidoEntity buscarHistorial(Long id) {
        return historialRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Historial de estado de pedido no encontrado"));
    }

    private PedidoEntity buscarPedido(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado"));
    }

    private ParEstadoPedidoEntity buscarEstado(Integer id) {
        return parEstadoPedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estado de pedido no encontrado"));
    }

    private ParEstadoPedidoEntity buscarEstadoOpcional(Integer id) {
        return id == null ? null : buscarEstado(id);
    }
}