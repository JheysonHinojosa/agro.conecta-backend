package com.agroconecta.estudent.application.services.impl;

import com.agroconecta.estudent.application.dto.request.CambioEstadoPedidoRequest;
import com.agroconecta.estudent.application.dto.request.PedidoRequest;
import com.agroconecta.estudent.application.dto.response.PedidoResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.mapper.PedidoMapper;
import com.agroconecta.estudent.application.services.PedidoService;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.*;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final CompradorRepository compradorRepository;
    private final CentroAcopioRepository centroAcopioRepository;
    private final ParMonedaRepository monedaRepository;
    private final ParEstadoPedidoRepository estadoPedidoRepository;
    private final HistorialEstadoPedidoRepository historialRepository;
    private final DetallePedidoRepository detallePedidoRepository;
    private final PedidoMapper pedidoMapper;

    @Override
    public PedidoResponse crear(PedidoRequest request) {

        String codigo = request.codigoPedido().trim().toUpperCase(Locale.ROOT);
        if (pedidoRepository.existsByCodigoPedido(codigo)) {
            throw new IllegalArgumentException("El código de pedido ya está en uso");
        }

        ParEstadoPedidoEntity estadoInicial = estadoPedidoRepository
                .findFirstByActivoTrueOrderByOrdenAsc()
                .orElseThrow(() -> new IllegalStateException("No hay estados de pedido activos configurados"));

        PedidoEntity entity = pedidoMapper.toEntity(request);
        entity.setCodigoPedido(codigo);
        asignarRelaciones(entity, request);
        entity.setEstadoPedido(estadoInicial);
        entity.setFechaPedido(OffsetDateTime.now());
        entity.setMontoTotal(BigDecimal.ZERO);

        PedidoEntity saved = pedidoRepository.save(entity);
        registrarHistorial(saved, null, estadoInicial, "Pedido creado");

        return pedidoMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoResponse> listar() {
        return pedidoRepository.findAllConRelaciones()
                .stream()
                .map(pedidoMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoResponse buscarPorId(Long id) {
        return pedidoMapper.toResponse(buscarPedido(id));
    }

    @Override
    public PedidoResponse actualizar(Long id, PedidoRequest request) {

        PedidoEntity entity = buscarPedido(id);
        validarNoFinal(entity);

        boolean cambiaMoneda = !entity.getMoneda().getIdMoneda().equals(request.idMoneda());
        if (cambiaMoneda && detallePedidoRepository.existsByPedidoIdPedido(id)) {
            throw new IllegalArgumentException("No se puede cambiar la moneda de un pedido con detalles");
        }

        pedidoMapper.updateEntity(entity, request);
        asignarRelaciones(entity, request);

        return pedidoMapper.toResponse(pedidoRepository.save(entity));
    }

    @Override
    public PedidoResponse cambiarEstado(Long id, CambioEstadoPedidoRequest request) {

        PedidoEntity pedido = buscarPedido(id);
        ParEstadoPedidoEntity actual = pedido.getEstadoPedido();
        validarNoFinal(pedido);

        ParEstadoPedidoEntity nuevo = estadoPedidoRepository.findById(request.idEstadoNuevo())
                .orElseThrow(() -> new EntityNotFoundException("Estado de pedido no encontrado"));

        if (!nuevo.isActivo()) {
            throw new IllegalArgumentException("El estado de pedido está inactivo");
        }
        if (nuevo.getIdEstadoPedido().equals(actual.getIdEstadoPedido())) {
            throw new IllegalArgumentException("El pedido ya está en ese estado");
        }
        if (!nuevo.isEsFinal() && nuevo.getOrden() <= actual.getOrden()) {
            throw new IllegalArgumentException("No se puede retroceder el estado del pedido");
        }

        pedido.setEstadoPedido(nuevo);
        PedidoEntity saved = pedidoRepository.save(pedido);
        registrarHistorial(saved, actual, nuevo, request.comentario());

        return pedidoMapper.toResponse(saved);
    }

    @Override
    public void recalcularMonto(Long idPedido) {
        PedidoEntity pedido = buscarPedido(idPedido);
        pedido.setMontoTotal(detallePedidoRepository.sumarSubtotales(idPedido));
        pedidoRepository.save(pedido);
    }


    private void asignarRelaciones(PedidoEntity entity, PedidoRequest request) {
        CompradorEntity comprador = compradorRepository.findById(request.idComprador())
                .orElseThrow(() -> new EntityNotFoundException("Comprador no encontrado"));
        if (!comprador.isActivo()) {
            throw new IllegalArgumentException("El comprador está inactivo");
        }

        CentroAcopioEntity centro = centroAcopioRepository.findById(request.idCentroAcopio())
                .orElseThrow(() -> new EntityNotFoundException("Centro de acopio no encontrado"));
        if (!centro.isActivo()) {
            throw new IllegalArgumentException("El centro de acopio está inactivo");
        }

        ParMonedaEntity moneda = monedaRepository.findById(request.idMoneda())
                .orElseThrow(() -> new EntityNotFoundException("Moneda no encontrada"));
        if (!moneda.isActivo()) {
            throw new IllegalArgumentException("La moneda está inactiva");
        }

        entity.setComprador(comprador);
        entity.setCentroAcopio(centro);
        entity.setMoneda(moneda);
    }

    private void validarNoFinal(PedidoEntity pedido) {
        if (pedido.getEstadoPedido().isEsFinal()) {
            throw new IllegalArgumentException("El pedido está en un estado final y no puede modificarse");
        }
    }

    private void registrarHistorial(PedidoEntity pedido, ParEstadoPedidoEntity anterior,
                                    ParEstadoPedidoEntity nuevo, String comentario) {
        HistorialEstadoPedidoEntity historial = new HistorialEstadoPedidoEntity();
        historial.setPedido(pedido);
        historial.setEstadoAnterior(anterior);
        historial.setEstadoNuevo(nuevo);
        historial.setFechaCambio(OffsetDateTime.now());
        historial.setComentario(comentario);
        historialRepository.save(historial);
    }

    private PedidoEntity buscarPedido(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado"));
    }
}