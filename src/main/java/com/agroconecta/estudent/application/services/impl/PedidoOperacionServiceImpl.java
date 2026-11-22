package com.agroconecta.estudent.application.services.impl;

import com.agroconecta.estudent.application.dto.request.PedidoOperacionDetalleRequest;
import com.agroconecta.estudent.application.dto.request.PedidoOperacionEstadoRequest;
import com.agroconecta.estudent.application.dto.request.PedidoOperacionRequest;
import com.agroconecta.estudent.application.dto.response.DetallePedidoResponse;
import com.agroconecta.estudent.application.dto.response.HistorialEstadoPedidoResponse;
import com.agroconecta.estudent.application.dto.response.PedidoOperacionResponse;
import com.agroconecta.estudent.application.dto.response.PedidoResponse;
import com.agroconecta.estudent.application.services.PedidoOperacionService;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.*;
import com.agroconecta.estudent.infrastructure.adapter.out.mapper.DetallePedidoMapper;
import com.agroconecta.estudent.infrastructure.adapter.out.mapper.HistorialEstadoPedidoMapper;
import com.agroconecta.estudent.infrastructure.adapter.out.mapper.PedidoMapper;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.Normalizer;
import java.time.OffsetDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class PedidoOperacionServiceImpl implements PedidoOperacionService {

    private static final String TIPO_SALIDA = "SALIDA_PEDIDO";
    private static final String TIPO_DEVOLUCION = "DEVOLUCION_PEDIDO";
    private static final String MARCA_SALIDA = "PEDIDO_OPERACION:SALIDA:";
    private static final String MARCA_DEVOLUCION = "PEDIDO_OPERACION:DEVOLUCION:";
    private static final Set<String> ESTADOS_VALIDOS =
            Set.of("REGISTRADO", "EN_TRANSITO", "ENTREGADO", "CANCELADO");

    private final PedidoRepository pedidoRepository;
    private final CompradorRepository compradorRepository;
    private final CentroAcopioRepository centroAcopioRepository;
    private final ProductoRepository productoRepository;
    private final LoteRepository loteRepository;
    private final InventarioRepository inventarioRepository;
    private final DetallePedidoRepository detallePedidoRepository;
    private final HistorialEstadoPedidoRepository historialRepository;
    private final MovimientoInventarioRepository movimientoRepository;
    private final ParEstadoPedidoRepository estadoPedidoRepository;
    private final ParTipoMovimientoRepository tipoMovimientoRepository;
    private final PedidoMapper pedidoMapper;
    private final DetallePedidoMapper detallePedidoMapper;
    private final HistorialEstadoPedidoMapper historialMapper;

    @Override
    public PedidoOperacionResponse crear(PedidoOperacionRequest request) {
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

        List<LineaValidada> lineas = validarLineas(request, centro);
        ParMonedaEntity moneda = lineas.get(0).producto().getMoneda();
        ParEstadoPedidoEntity estadoRegistrado = buscarEstadoInicial();
        ParTipoMovimientoEntity tipoSalida = buscarTipoMovimiento(TIPO_SALIDA, (short) -1);

        List<LineaConInventario> lineasConInventario = bloquearYValidarStock(lineas, centro);
        Long secuencia = pedidoRepository.getNextCodigoPedidoSequence();
        if (secuencia == null || secuencia < 1) {
            throw new IllegalStateException("No se pudo generar el código del pedido");
        }

        PedidoEntity pedido = new PedidoEntity();
        pedido.setCodigoPedido(String.format(Locale.ROOT, "PED-%010d", secuencia));
        pedido.setComprador(comprador);
        pedido.setCentroAcopio(centro);
        pedido.setMoneda(moneda);
        pedido.setEstadoPedido(estadoRegistrado);
        pedido.setFechaPedido(OffsetDateTime.now());
        pedido.setFechaEntregaEstimada(request.fechaEntregaEstimada());
        pedido.setObservacion(request.observacion());
        pedido.setMontoTotal(BigDecimal.ZERO.setScale(2));
        pedido = pedidoRepository.saveAndFlush(pedido);

        OffsetDateTime fechaMovimiento = OffsetDateTime.now();
        registrarHistorial(pedido, null, estadoRegistrado, "Pedido creado");

        List<DetallePedidoEntity> detalles = new ArrayList<>();
        BigDecimal montoTotal = BigDecimal.ZERO;
        for (LineaConInventario linea : lineasConInventario) {
            BigDecimal precio = linea.linea().producto().getPrecioReferencial();
            DetallePedidoEntity detalle = new DetallePedidoEntity();
            detalle.setPedido(pedido);
            detalle.setProducto(linea.linea().producto());
            detalle.setLote(linea.linea().lote());
            detalle.setCantidad(linea.linea().request().cantidad());
            detalle.setPrecioUnitario(precio);
            detalles.add(detalle);
            montoTotal = montoTotal.add(calcularSubtotal(detalle.getCantidad(), precio));

            registrarMovimiento(
                    pedido,
                    linea.inventario(),
                    linea.linea().lote(),
                    tipoSalida,
                    detalle.getCantidad(),
                    MARCA_SALIDA + "Descuento por creación del pedido " + pedido.getCodigoPedido(),
                    fechaMovimiento
            );
        }
        detallePedidoRepository.saveAllAndFlush(detalles);

        pedido.setMontoTotal(montoTotal.setScale(2, RoundingMode.HALF_UP));
        pedidoRepository.save(pedido);
        return respuesta(pedido);
    }

    @Override
    public PedidoOperacionResponse cambiarEstado(Long idPedido, PedidoOperacionEstadoRequest request) {
        PedidoEntity pedido = pedidoRepository.findByIdForUpdate(idPedido)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado"));
        ParEstadoPedidoEntity estadoNuevo = estadoPedidoRepository.findById(request.idEstadoNuevo())
                .orElseThrow(() -> new EntityNotFoundException("Estado de pedido no encontrado"));

        if (!estadoNuevo.isActivo()) {
            throw new IllegalArgumentException("El estado de pedido está inactivo");
        }

        String estadoActual = estadoClave(pedido.getEstadoPedido());
        String estadoDestino = estadoClave(estadoNuevo);
        validarTransicion(estadoActual, estadoDestino);

        if ("CANCELADO".equals(estadoDestino)) {
            reponerStock(pedido);
        }

        ParEstadoPedidoEntity estadoAnterior = pedido.getEstadoPedido();
        pedido.setEstadoPedido(estadoNuevo);
        PedidoEntity guardado = pedidoRepository.save(pedido);
        registrarHistorial(guardado, estadoAnterior, estadoNuevo, request.comentario());
        return respuesta(guardado);
    }

    private List<LineaValidada> validarLineas(PedidoOperacionRequest request, CentroAcopioEntity centro) {
        if (request.detalle() == null || request.detalle().isEmpty()) {
            throw new IllegalArgumentException("El pedido debe incluir al menos un detalle");
        }

        Set<Long> productosIncluidos = new HashSet<>();
        List<LineaValidada> lineas = new ArrayList<>();
        Integer idMoneda = null;
        for (PedidoOperacionDetalleRequest detalleRequest : request.detalle()) {
            if (detalleRequest == null) {
                throw new IllegalArgumentException("El detalle del pedido no puede estar vacío");
            }
            if (!productosIncluidos.add(detalleRequest.idProducto())) {
                throw new IllegalArgumentException(
                        "No se permiten productos repetidos en el pedido: " + detalleRequest.idProducto());
            }

            ProductoEntity producto = productoRepository.findById(detalleRequest.idProducto())
                    .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
            if (!producto.isActivo()) {
                throw new IllegalArgumentException("El producto está inactivo");
            }
            if (producto.getPrecioReferencial() == null || producto.getPrecioReferencial().signum() <= 0) {
                throw new IllegalArgumentException("El producto no tiene un precio referencial válido");
            }
            ParMonedaEntity monedaProducto = producto.getMoneda();
            if (monedaProducto == null || !monedaProducto.isActivo()) {
                throw new IllegalArgumentException("El producto no tiene una moneda activa");
            }
            if (idMoneda == null) {
                idMoneda = monedaProducto.getIdMoneda();
            } else if (!idMoneda.equals(monedaProducto.getIdMoneda())) {
                throw new IllegalArgumentException("No se pueden mezclar monedas en un pedido");
            }

            LoteEntity lote = loteRepository.findById(detalleRequest.idLote())
                    .orElseThrow(() -> new EntityNotFoundException("Lote no encontrado"));
            if (!lote.isActivo()) {
                throw new IllegalArgumentException("El lote está inactivo");
            }
            if (lote.getProducto() == null
                    || !Objects.equals(lote.getProducto().getIdProducto(), producto.getIdProducto())) {
                throw new IllegalArgumentException("El lote no corresponde al producto indicado");
            }
            if (lote.getCentroAcopio() == null
                    || !Objects.equals(lote.getCentroAcopio().getIdCentroAcopio(), centro.getIdCentroAcopio())) {
                throw new IllegalArgumentException("El lote no pertenece al centro de acopio del pedido");
            }
            lineas.add(new LineaValidada(detalleRequest, producto, lote));
        }
        lineas.sort(Comparator.comparing(linea -> linea.producto().getIdProducto()));
        return lineas;
    }

    private List<LineaConInventario> bloquearYValidarStock(
            List<LineaValidada> lineas, CentroAcopioEntity centro) {
        List<LineaConInventario> lineasConInventario = new ArrayList<>();
        for (LineaValidada linea : lineas) {
            InventarioEntity inventario = inventarioRepository.findByCentroAndProductoForUpdate(
                            centro.getIdCentroAcopio(), linea.producto().getIdProducto())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "No existe inventario para el producto " + linea.producto().getIdProducto()
                                    + " en el centro de acopio"));
            BigDecimal disponible = stockActual(inventario);
            if (disponible.compareTo(linea.request().cantidad()) < 0) {
                throw new IllegalArgumentException(
                        "Stock insuficiente para el producto " + linea.producto().getIdProducto()
                                + ": disponible " + disponible + ", solicitado " + linea.request().cantidad());
            }
            lineasConInventario.add(new LineaConInventario(linea, inventario));
        }
        return lineasConInventario;
    }

    private void reponerStock(PedidoEntity pedido) {
        List<MovimientoInventarioEntity> salidas = movimientoRepository
                .findByPedidoIdPedidoAndObservacionStartingWith(pedido.getIdPedido(), MARCA_SALIDA);
        if (salidas.isEmpty()) {
            throw new IllegalArgumentException(
                    "No se encontró la salida de stock de esta operación para el pedido");
        }
        if (!movimientoRepository.findByPedidoIdPedidoAndObservacionStartingWith(
                pedido.getIdPedido(), MARCA_DEVOLUCION).isEmpty()) {
            throw new IllegalArgumentException("El stock de este pedido ya fue repuesto");
        }

        List<DetallePedidoEntity> detalles = detallePedidoRepository
                .findByPedidoIdPedidoOrderByIdDetallePedidoAsc(pedido.getIdPedido());
        if (detalles.isEmpty()) {
            throw new IllegalArgumentException("El pedido no tiene detalles para reponer");
        }
        ParTipoMovimientoEntity tipoDevolucion = buscarTipoMovimiento(TIPO_DEVOLUCION, (short) 1);

        Map<Long, InventarioEntity> inventarios = new LinkedHashMap<>();
        detalles.stream()
                .map(detalle -> detalle.getProducto().getIdProducto())
                .distinct()
                .sorted()
                .forEach(idProducto -> {
                    InventarioEntity inventario = inventarioRepository.findByCentroAndProductoForUpdate(
                                    pedido.getCentroAcopio().getIdCentroAcopio(), idProducto)
                            .orElseThrow(() -> new IllegalArgumentException(
                                    "No existe inventario para reponer el producto " + idProducto));
                    inventarios.put(idProducto, inventario);
                });

        OffsetDateTime fechaMovimiento = OffsetDateTime.now();
        List<MovimientoInventarioEntity> devoluciones = new ArrayList<>();
        for (DetallePedidoEntity detalle : detalles) {
            InventarioEntity inventario = inventarios.get(detalle.getProducto().getIdProducto());
            BigDecimal anterior = stockActual(inventario);
            BigDecimal posterior = anterior.add(detalle.getCantidad());
            inventario.setStockActual(posterior);
            MovimientoInventarioEntity movimiento = crearMovimiento(
                    pedido,
                    inventario,
                    detalle.getLote(),
                    tipoDevolucion,
                    detalle.getCantidad(),
                    MARCA_DEVOLUCION + "Reposición por cancelación del pedido " + pedido.getCodigoPedido(),
                    fechaMovimiento,
                    anterior,
                    posterior
            );
            devoluciones.add(movimiento);
        }
        inventarios.values().forEach(inventarioRepository::save);
        movimientoRepository.saveAllAndFlush(devoluciones);
    }

    private void registrarMovimiento(
            PedidoEntity pedido,
            InventarioEntity inventario,
            LoteEntity lote,
            ParTipoMovimientoEntity tipo,
            BigDecimal cantidad,
            String observacion,
            OffsetDateTime fechaMovimiento) {
        BigDecimal anterior = stockActual(inventario);
        BigDecimal posterior = anterior.subtract(cantidad);
        inventario.setStockActual(posterior);
        inventarioRepository.save(inventario);
        movimientoRepository.save(crearMovimiento(
                pedido, inventario, lote, tipo, cantidad, observacion, fechaMovimiento, anterior, posterior));
    }

    private MovimientoInventarioEntity crearMovimiento(
            PedidoEntity pedido,
            InventarioEntity inventario,
            LoteEntity lote,
            ParTipoMovimientoEntity tipo,
            BigDecimal cantidad,
            String observacion,
            OffsetDateTime fechaMovimiento,
            BigDecimal anterior,
            BigDecimal posterior) {
        MovimientoInventarioEntity movimiento = new MovimientoInventarioEntity();
        movimiento.setPedido(pedido);
        movimiento.setInventario(inventario);
        movimiento.setLote(lote);
        movimiento.setTipoMovimiento(tipo);
        movimiento.setCantidad(cantidad);
        movimiento.setStockAnterior(anterior);
        movimiento.setStockPosterior(posterior);
        movimiento.setObservacion(observacion);
        movimiento.setFechaMovimiento(fechaMovimiento);
        return movimiento;
    }

    private ParEstadoPedidoEntity buscarEstadoInicial() {
        List<ParEstadoPedidoEntity> encontrados = estadoPedidoRepository.findAllByActivoTrue().stream()
                .filter(estado -> "REGISTRADO".equals(estadoClave(estado)))
                .toList();
        if (encontrados.size() != 1) {
            throw new IllegalStateException("Debe existir exactamente un estado activo REGISTRADO");
        }
        return encontrados.get(0);
    }

    private ParTipoMovimientoEntity buscarTipoMovimiento(String codigo, short signoEsperado) {
        ParTipoMovimientoEntity tipo = tipoMovimientoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new IllegalStateException(
                        "No está configurado el tipo de movimiento " + codigo));
        if (!tipo.isActivo() || tipo.getSigno() == null || tipo.getSigno() != signoEsperado) {
            throw new IllegalStateException(
                    "El tipo de movimiento " + codigo + " debe estar activo y tener signo " + signoEsperado);
        }
        return tipo;
    }

    private void validarTransicion(String actual, String destino) {
        if (actual == null || destino == null || !ESTADOS_VALIDOS.contains(destino)) {
            throw new IllegalArgumentException("El estado del pedido no es válido para esta operación");
        }
        boolean permitida = switch (actual) {
            case "REGISTRADO" -> "EN_TRANSITO".equals(destino) || "CANCELADO".equals(destino);
            case "EN_TRANSITO" -> "ENTREGADO".equals(destino) || "CANCELADO".equals(destino);
            default -> false;
        };
        if (!permitida) {
            throw new IllegalArgumentException(
                    "Transición de estado no permitida: " + actual + " -> " + destino);
        }
    }

    private String estadoClave(ParEstadoPedidoEntity estado) {
        if (estado == null) {
            return null;
        }
        String codigo = normalizarEstado(estado.getCodigo());
        if (ESTADOS_VALIDOS.contains(codigo)) {
            return codigo;
        }
        String nombre = normalizarEstado(estado.getNombre());
        return ESTADOS_VALIDOS.contains(nombre) ? nombre : null;
    }

    private String normalizarEstado(String valor) {
        if (valor == null || valor.isBlank()) {
            return null;
        }
        String sinAcentos = Normalizer.normalize(valor.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return sinAcentos.toUpperCase(Locale.ROOT)
                .replaceAll("[^A-Z0-9]+", "_")
                .replaceAll("^_|_$", "");
    }

    private BigDecimal calcularSubtotal(BigDecimal cantidad, BigDecimal precio) {
        return cantidad.multiply(precio).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal stockActual(InventarioEntity inventario) {
        return inventario.getStockActual() == null ? BigDecimal.ZERO : inventario.getStockActual();
    }

    private PedidoOperacionResponse respuesta(PedidoEntity pedido) {
        PedidoResponse cabecera = pedidoMapper.toResponse(pedido);
        List<DetallePedidoResponse> detalles =
                detallePedidoRepository.findByPedidoIdPedidoOrderByIdDetallePedidoAsc(pedido.getIdPedido())
                        .stream()
                        .map(detallePedidoMapper::toResponse)
                        .toList();
        List<HistorialEstadoPedidoResponse> historial =
                historialRepository.findByPedidoIdPedidoOrderByFechaCambioAscIdHistorialAsc(pedido.getIdPedido())
                        .stream()
                        .map(historialMapper::toResponse)
                        .toList();
        return PedidoOperacionResponse.from(cabecera, detalles, historial);
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

    private record LineaValidada(
            PedidoOperacionDetalleRequest request,
            ProductoEntity producto,
            LoteEntity lote) {}

    private record LineaConInventario(LineaValidada linea, InventarioEntity inventario) {}
}
