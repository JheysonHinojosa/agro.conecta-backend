package com.agroconecta.estudent.application.services.impl;

import com.agroconecta.estudent.application.dto.response.BootstrapResponse;
import com.agroconecta.estudent.application.dto.response.BootstrapResponse.*;
import com.agroconecta.estudent.application.services.BootstrapService;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.*;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BootstrapServiceImpl implements BootstrapService {

    private final ParPaisRepository parPaisRepository;
    private final ParCategoriaProductoRepository parCategoriaProductoRepository;
    private final CentroAcopioRepository centroAcopioRepository;
    private final ProductorRepository productorRepository;
    private final CompradorRepository compradorRepository;
    private final ProductoRepository productoRepository;
    private final InventarioRepository inventarioRepository;
    private final LoteRepository loteRepository;
    private final PedidoRepository pedidoRepository;
    private final DetallePedidoRepository detallePedidoRepository;
    private final HistorialEstadoPedidoRepository historialEstadoPedidoRepository;
    private final ResenaLoteRepository resenaLoteRepository;
    private final SensorIOTRepository sensorIOTRepository;
    private final LecturaSensorRepository lecturaSensorRepository;
    private final UsuarioRepository usuarioRepository;

    private static final java.time.format.DateTimeFormatter ISO_UTC_FORMATTER =
            java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .withZone(java.time.ZoneOffset.UTC);

    private String formatearFechaUtc(java.time.OffsetDateTime odt) {
        return odt != null ? ISO_UTC_FORMATTER.format(odt) : null;
    }

    @Override
    @Transactional(readOnly = true)
    public BootstrapResponse obtenerSnapshot(Authentication authentication) {
        log.info("Generando snapshot de bootstrap para usuario: {}",
                authentication != null ? authentication.getName() : "anónimo");

        // 1. Países
        List<PaisBootstrapDto> paises = parPaisRepository.findAllConMoneda()
                .stream()
                .filter(ParPaisEntity::isActivo)
                .map(p -> new PaisBootstrapDto(
                        p.getIdPais(),
                        p.getNombre(),
                        p.getMoneda() != null ? p.getMoneda().getCodigo() : "PEN"
                ))
                .toList();

        // 2. Categorías (utiliza solo nombre que es NOT NULL según contrato y DDL)
        List<CategoriaBootstrapDto> categorias = parCategoriaProductoRepository.findAll()
                .stream()
                .filter(ParCategoriaProductoEntity::isActivo)
                .map(c -> new CategoriaBootstrapDto(
                        c.getIdCategoria(),
                        c.getNombre()
                ))
                .toList();

        // 3. Centros de Acopio
        List<CentroBootstrapDto> centros = centroAcopioRepository.findAll()
                .stream()
                .filter(CentroAcopioEntity::isActivo)
                .map(c -> new CentroBootstrapDto(
                        c.getIdCentroAcopio(),
                        c.getCodigo(),
                        c.getNombre(),
                        c.getPais() != null ? c.getPais().getIdPais() : null,
                        c.getCiudad()
                ))
                .toList();

        // 4. Productores
        List<ProductorBootstrapDto> productores = productorRepository.findAllConRelaciones()
                .stream()
                .filter(ProductorEntity::isActivo)
                .map(p -> new ProductorBootstrapDto(
                        p.getIdProductor(),
                        p.getTipoDocumento() != null ? p.getTipoDocumento().getIdTipoDocumento() : null,
                        p.getNroDocumento(),
                        p.getNombreRazonSocial(),
                        p.getPais() != null ? p.getPais().getIdPais() : null,
                        p.getCentroAcopio() != null ? p.getCentroAcopio().getIdCentroAcopio() : null,
                        p.getCategoriaPrincipal() != null ? p.getCategoriaPrincipal().getIdCategoria() : null
                ))
                .toList();

        // 5. Compradores
        List<CompradorBootstrapDto> compradores = compradorRepository.findAllConRelaciones()
                .stream()
                .filter(CompradorEntity::isActivo)
                .map(c -> new CompradorBootstrapDto(
                        c.getIdComprador(),
                        c.getTipoComprador() != null ? c.getTipoComprador().getIdTipoComprador() : null,
                        c.getTipoDocumento() != null ? c.getTipoDocumento().getIdTipoDocumento() : null,
                        c.getNroDocumento(),
                        c.getNombreRazonSocial(),
                        c.getPais() != null ? c.getPais().getIdPais() : null
                ))
                .toList();

        // 6. Productos
        List<ProductoBootstrapDto> productos = productoRepository.findAllConRelaciones()
                .stream()
                .filter(ProductoEntity::isActivo)
                .map(pr -> new ProductoBootstrapDto(
                        pr.getIdProducto(),
                        pr.getCodigo(),
                        pr.getNombre(),
                        pr.getCategoria() != null ? pr.getCategoria().getIdCategoria() : null,
                        pr.getUnidadMedida() != null ? pr.getUnidadMedida().getIdUnidadMedida() : null,
                        pr.getPrecioReferencial(),
                        pr.getMoneda() != null ? pr.getMoneda().getCodigo() : "PEN",
                        pr.getProductor() != null ? pr.getProductor().getIdProductor() : null
                ))
                .toList();

        // 7. Inventario
        List<InventarioBootstrapDto> inventario = inventarioRepository.findAllConRelaciones()
                .stream()
                .map(i -> new InventarioBootstrapDto(
                        i.getIdInventario(),
                        i.getCentroAcopio() != null ? i.getCentroAcopio().getIdCentroAcopio() : null,
                        i.getProducto() != null ? i.getProducto().getIdProducto() : null,
                        i.getStockActual(),
                        i.getStockMinimo()
                ))
                .toList();

        // 8. Lotes
        List<LoteBootstrapDto> lotes = loteRepository.findAllConRelaciones()
                .stream()
                .filter(LoteEntity::isActivo)
                .map(l -> new LoteBootstrapDto(
                        l.getIdLote(),
                        l.getCodigoLote(),
                        l.getProducto() != null ? l.getProducto().getIdProducto() : null,
                        l.getCentroAcopio() != null ? l.getCentroAcopio().getIdCentroAcopio() : null,
                        l.getFechaCosecha() != null ? l.getFechaCosecha().toString() : null,
                        l.getCantidadIngresada()
                ))
                .toList();

        // 9. Pedidos (con detalle e historial agrupados)
        Map<Long, List<DetallePedidoBootstrapDto>> detallesPorPedido = detallePedidoRepository.findAllConRelaciones()
                .stream()
                .collect(Collectors.groupingBy(
                        d -> d.getPedido().getIdPedido(),
                        Collectors.mapping(d -> new DetallePedidoBootstrapDto(
                                d.getProducto() != null ? d.getProducto().getIdProducto() : null,
                                d.getLote() != null ? d.getLote().getIdLote() : null,
                                d.getCantidad(),
                                d.getPrecioUnitario()
                        ), Collectors.toList())
                ));

        Map<Long, List<HistorialPedidoBootstrapDto>> historialPorPedido = historialEstadoPedidoRepository.findAllConRelaciones()
                .stream()
                .collect(Collectors.groupingBy(
                        h -> h.getPedido().getIdPedido(),
                        Collectors.mapping(h -> new HistorialPedidoBootstrapDto(
                                h.getEstadoNuevo() != null ? h.getEstadoNuevo().getNombre() : "Registrado",
                                formatearFechaUtc(h.getFechaCambio())
                        ), Collectors.toList())
                ));

        List<PedidoBootstrapDto> pedidos = pedidoRepository.findAllConRelaciones()
                .stream()
                .map(p -> new PedidoBootstrapDto(
                        p.getIdPedido(),
                        p.getCodigoPedido(),
                        p.getComprador() != null ? p.getComprador().getIdComprador() : null,
                        p.getCentroAcopio() != null ? p.getCentroAcopio().getIdCentroAcopio() : null,
                        p.getEstadoPedido() != null ? p.getEstadoPedido().getNombre() : "Registrado",
                        p.getMoneda() != null ? p.getMoneda().getCodigo() : "PEN",
                        p.getMontoTotal(),
                        formatearFechaUtc(p.getFechaPedido()),
                        detallesPorPedido.getOrDefault(p.getIdPedido(), List.of()),
                        historialPorPedido.getOrDefault(p.getIdPedido(), List.of())
                ))
                .toList();

        // 10. Reseñas
        List<ResenaBootstrapDto> resenas = resenaLoteRepository.findAllConRelaciones()
                .stream()
                .map(r -> new ResenaBootstrapDto(
                        r.getIdResena(),
                        r.getLote() != null ? r.getLote().getIdLote() : null,
                        r.getComprador() != null ? r.getComprador().getIdComprador() : null,
                        r.getPedido() != null ? r.getPedido().getIdPedido() : null,
                        r.getPuntuacion(),
                        r.getComentario()
                ))
                .toList();

        // 11. Sensores (con su última lectura)
        Map<Long, LecturaSensorEntity> ultimasLecturas = lecturaSensorRepository.findUltimasLecturasPorSensor()
                .stream()
                .filter(l -> l.getSensor() != null)
                .collect(Collectors.toMap(
                        l -> l.getSensor().getIdSensor(),
                        l -> l,
                        (existente, reemplazo) -> existente
                ));

        List<SensorBootstrapDto> sensores = sensorIOTRepository.findAllConCentroAcopio()
                .stream()
                .filter(SensorIotEntity::isActivo)
                .map(s -> {
                    LecturaSensorEntity ultima = ultimasLecturas.get(s.getIdSensor());
                    return new SensorBootstrapDto(
                            s.getIdSensor(),
                            s.getCodigoSensor(),
                            s.getCentroAcopio() != null ? s.getCentroAcopio().getIdCentroAcopio() : null,
                            s.getTempMinC(),
                            s.getTempMaxC(),
                            s.getHumedadMinPct(),
                            s.getHumedadMaxPct(),
                            ultima != null ? ultima.getTemperaturaC() : null,
                            ultima != null ? ultima.getHumedadPct() : null,
                            ultima != null ? formatearFechaUtc(ultima.getFechaLectura()) : null
                    );
                })
                .toList();

        // 12. Usuarios: Solo visibles para ADMINISTRADOR. El OPERADOR recibe array vacío []
        boolean esAdmin = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equalsIgnoreCase("ADMINISTRADOR")
                        || a.getAuthority().equalsIgnoreCase("ROLE_ADMINISTRADOR"));

        List<UsuarioBootstrapDto> usuarios = List.of();
        if (esAdmin) {
            usuarios = usuarioRepository.findAllConRol()
                    .stream()
                    .filter(UsuarioEntity::isActivo)
                    .map(u -> new UsuarioBootstrapDto(
                            u.getIdUsuario(),
                            u.getUsername(),
                            u.getEmail(),
                            u.getRol() != null && "ADMINISTRADOR".equalsIgnoreCase(u.getRol().getCodigo())
                                    ? "Administrador" : "Operador"
                    ))
                    .toList();
        }

        return new BootstrapResponse(
                paises,
                categorias,
                centros,
                productores,
                compradores,
                productos,
                inventario,
                lotes,
                pedidos,
                resenas,
                sensores,
                usuarios
        );
    }
}
