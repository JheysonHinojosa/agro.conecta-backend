package com.agroconecta.estudent.application.services.impl;

import com.agroconecta.estudent.application.dto.request.MovimientoInventarioRequest;
import com.agroconecta.estudent.application.dto.response.MovimientoInventarioResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.mapper.MovimientoInventarioMapper;
import com.agroconecta.estudent.application.services.MovimientoInventarioService;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.InventarioEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.LoteEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.MovimientoInventarioEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParTipoMovimientoEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.PedidoEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.InventarioRepository;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.LoteRepository;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.MovimientoInventarioRepository;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.ParTipoMovimientoRepository;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MovimientoInventarioServiceImpl implements MovimientoInventarioService {

    private final MovimientoInventarioRepository movimientoRepository;
    private final InventarioRepository inventarioRepository;
    private final ParTipoMovimientoRepository tipoMovimientoRepository;
    private final PedidoRepository pedidoRepository;
    private final LoteRepository loteRepository;
    private final MovimientoInventarioMapper movimientoMapper;

    @Override
    public MovimientoInventarioResponse crear(MovimientoInventarioRequest request) {

        // Bloquea la fila del inventario hasta terminar la transacción
        InventarioEntity inventario = inventarioRepository.findByIdForUpdate(request.idInventario())
                .orElseThrow(() -> new EntityNotFoundException("Inventario no encontrado"));

        ParTipoMovimientoEntity tipo = buscarTipoMovimiento(request.idTipoMovimiento());
        int signo = obtenerSigno(tipo);

        LoteEntity lote = null;
        if (request.idLote() != null) {
            lote = buscarLote(request.idLote());
            validarLoteContraInventario(lote, inventario);
        }

        PedidoEntity pedido = request.idPedido() == null ? null : buscarPedido(request.idPedido());

        // Cálculo del stock (lo decide el servidor, no el cliente)
        BigDecimal stockAnterior = inventario.getStockActual() != null
                ? inventario.getStockActual() : BigDecimal.ZERO;
        BigDecimal stockPosterior = stockAnterior
                .add(request.cantidad().multiply(BigDecimal.valueOf(signo)));

        if (stockPosterior.signum() < 0) {
            throw new IllegalArgumentException(
                    "Stock insuficiente: disponible " + stockAnterior + ", solicitado " + request.cantidad());
        }

        MovimientoInventarioEntity movimiento = movimientoMapper.toEntity(request);
        movimiento.setInventario(inventario);
        movimiento.setTipoMovimiento(tipo);
        movimiento.setPedido(pedido);
        movimiento.setLote(lote);
        movimiento.setStockAnterior(stockAnterior);
        movimiento.setStockPosterior(stockPosterior);

        if (movimiento.getFechaMovimiento() == null) {
            movimiento.setFechaMovimiento(OffsetDateTime.now());
        }

        inventario.setStockActual(stockPosterior);
        inventarioRepository.save(inventario);

        return movimientoMapper.toResponse(movimientoRepository.save(movimiento));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoInventarioResponse> listar() {
        return movimientoRepository.findAllConRelaciones()
                .stream()
                .map(movimientoMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MovimientoInventarioResponse buscarPorId(Long id) {
        return movimientoRepository.findById(id)
                .map(movimientoMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Movimiento de inventario no encontrado"));
    }

    // ---------- métodos privados ----------

    /** El signo del tipo de movimiento define si suma (+1) o resta (-1) stock. */
    private int obtenerSigno(ParTipoMovimientoEntity tipo) {
        if (!tipo.isActivo()) {
            throw new IllegalArgumentException("El tipo de movimiento está inactivo");
        }
        Short signo = tipo.getSigno();
        if (signo == null || (signo != 1 && signo != -1)) {
            throw new IllegalArgumentException("El tipo de movimiento no tiene un signo válido (+1 o -1)");
        }
        return signo;
    }

    /** El lote debe ser del mismo producto y del mismo centro de acopio que el inventario. */
    private void validarLoteContraInventario(LoteEntity lote, InventarioEntity inventario) {
        if (!lote.getProducto().getIdProducto().equals(inventario.getProducto().getIdProducto())) {
            throw new IllegalArgumentException("El lote no corresponde al producto del inventario");
        }
        if (!lote.getCentroAcopio().getIdCentroAcopio()
                .equals(inventario.getCentroAcopio().getIdCentroAcopio())) {
            throw new IllegalArgumentException("El lote no pertenece al centro de acopio del inventario");
        }
    }

    private ParTipoMovimientoEntity buscarTipoMovimiento(Integer id) {
        return tipoMovimientoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de movimiento no encontrado"));
    }

    private PedidoEntity buscarPedido(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado"));
    }

    private LoteEntity buscarLote(Long id) {
        return loteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lote no encontrado"));
    }
}