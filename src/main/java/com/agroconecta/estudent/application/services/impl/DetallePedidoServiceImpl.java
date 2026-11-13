package com.agroconecta.estudent.application.services.impl;

import com.agroconecta.estudent.application.dto.request.DetallePedidoRequest;
import com.agroconecta.estudent.application.dto.response.DetallePedidoResponse;
import com.agroconecta.estudent.application.services.DetallePedidoService;
import com.agroconecta.estudent.application.services.PedidoService;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.DetallePedidoEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.LoteEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.PedidoEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ProductoEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.mapper.DetallePedidoMapper;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.DetallePedidoRepository;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.LoteRepository;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.PedidoRepository;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DetallePedidoServiceImpl implements DetallePedidoService {

    private final DetallePedidoRepository detallePedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;
    private final LoteRepository loteRepository;
    private final DetallePedidoMapper detallePedidoMapper;
    private final PedidoService pedidoService;

    @Override
    public DetallePedidoResponse crear(DetallePedidoRequest request) {

        DetallePedidoEntity entity = detallePedidoMapper.toEntity(request);
        asignarRelaciones(entity, request);
        entity.setSubtotal(calcularSubtotal(entity));

        DetallePedidoEntity saved = detallePedidoRepository.save(entity);
        pedidoService.recalcularMonto(saved.getPedido().getIdPedido());

        return detallePedidoMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetallePedidoResponse> listar() {
        return detallePedidoRepository.findAll()
                .stream()
                .map(detallePedidoMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DetallePedidoResponse buscarPorId(Long id) {
        return detallePedidoMapper.toResponse(buscarDetalle(id));
    }

    @Override
    public DetallePedidoResponse actualizar(Long id, DetallePedidoRequest request) {

        DetallePedidoEntity entity = buscarDetalle(id);
        PedidoEntity pedidoAnterior = entity.getPedido();
        validarPedidoModificable(pedidoAnterior);

        detallePedidoMapper.updateEntity(entity, request);
        asignarRelaciones(entity, request);
        entity.setSubtotal(calcularSubtotal(entity));

        DetallePedidoEntity saved = detallePedidoRepository.save(entity);
        detallePedidoRepository.flush();

        Long idPedidoNuevo = saved.getPedido().getIdPedido();
        pedidoService.recalcularMonto(idPedidoNuevo);
        if (!pedidoAnterior.getIdPedido().equals(idPedidoNuevo)) {
            pedidoService.recalcularMonto(pedidoAnterior.getIdPedido());
        }

        return detallePedidoMapper.toResponse(saved);
    }

    @Override
    public void eliminar(Long id) {
        DetallePedidoEntity detalle = buscarDetalle(id);
        validarPedidoModificable(detalle.getPedido());

        Long idPedido = detalle.getPedido().getIdPedido();
        detallePedidoRepository.delete(detalle);
        detallePedidoRepository.flush();

        pedidoService.recalcularMonto(idPedido);
    }

    private void asignarRelaciones(DetallePedidoEntity entity, DetallePedidoRequest request) {
        PedidoEntity pedido = buscarPedido(request.idPedido());
        validarPedidoModificable(pedido);
        entity.setPedido(pedido);

        entity.setProducto(buscarProducto(request.idProducto()));

        LoteEntity lote = buscarLote(request.idLote());
        if (!lote.getProducto().getIdProducto().equals(request.idProducto())) {
            throw new IllegalArgumentException("El lote no corresponde al producto indicado");
        }
        entity.setLote(lote);
    }

    /** No se pueden agregar, editar ni quitar detalles de un pedido en estado final. */
    private void validarPedidoModificable(PedidoEntity pedido) {
        if (pedido.getEstadoPedido().isEsFinal()) {
            throw new IllegalArgumentException("El pedido está en un estado final y no puede modificarse");
        }
    }

    private BigDecimal calcularSubtotal(DetallePedidoEntity entity) {
        return entity.getCantidad()
                .multiply(entity.getPrecioUnitario())
                .setScale(2, RoundingMode.HALF_UP);
    }

    private DetallePedidoEntity buscarDetalle(Long id) {
        return detallePedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Detalle de pedido no encontrado"));
    }

    private PedidoEntity buscarPedido(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado"));
    }

    private ProductoEntity buscarProducto(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
    }

    private LoteEntity buscarLote(Long id) {
        return loteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lote no encontrado"));
    }
}