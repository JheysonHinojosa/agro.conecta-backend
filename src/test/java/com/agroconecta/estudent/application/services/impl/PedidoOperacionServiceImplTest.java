package com.agroconecta.estudent.application.services.impl;

import com.agroconecta.estudent.application.dto.request.PedidoOperacionDetalleRequest;
import com.agroconecta.estudent.application.dto.request.PedidoOperacionEstadoRequest;
import com.agroconecta.estudent.application.dto.request.PedidoOperacionRequest;
import com.agroconecta.estudent.application.dto.response.PedidoOperacionResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.*;
import com.agroconecta.estudent.infrastructure.adapter.out.mapper.DetallePedidoMapperImpl;
import com.agroconecta.estudent.infrastructure.adapter.out.mapper.HistorialEstadoPedidoMapperImpl;
import com.agroconecta.estudent.infrastructure.adapter.out.mapper.PedidoMapperImpl;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:pedido-operacion;MODE=PostgreSQL;DB_CLOSE_DELAY=-1",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.flyway.enabled=false"
})
@Import({
        PedidoOperacionServiceImpl.class,
        PedidoMapperImpl.class,
        DetallePedidoMapperImpl.class,
        HistorialEstadoPedidoMapperImpl.class
})
class PedidoOperacionServiceImplTest {

    @Autowired private PedidoOperacionServiceImpl service;
    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private CompradorRepository compradorRepository;
    @Autowired private CentroAcopioRepository centroAcopioRepository;
    @Autowired private ProductoRepository productoRepository;
    @Autowired private LoteRepository loteRepository;
    @Autowired private InventarioRepository inventarioRepository;
    @Autowired private DetallePedidoRepository detallePedidoRepository;
    @Autowired private HistorialEstadoPedidoRepository historialRepository;
    @Autowired private MovimientoInventarioRepository movimientoRepository;
    @Autowired private ParMonedaRepository monedaRepository;
    @Autowired private ParEstadoPedidoRepository estadoRepository;
    @Autowired private ParTipoMovimientoRepository tipoMovimientoRepository;
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private PlatformTransactionManager transactionManager;

    private TransactionTemplate isolatedTransaction;
    private Long idComprador;
    private Long idCentro;
    private Long idProducto;
    private Long idLote;
    private Long idInventario;
    private Integer idRegistrado;
    private Integer idTransito;
    private Integer idEntregado;
    private Integer idCancelado;

    @BeforeEach
    void setUp() {
        isolatedTransaction = new TransactionTemplate(transactionManager);
        isolatedTransaction.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        isolatedTransaction.executeWithoutResult(status -> {
            movimientoRepository.deleteAllInBatch();
            historialRepository.deleteAllInBatch();
            detallePedidoRepository.deleteAllInBatch();
            pedidoRepository.deleteAllInBatch();
            inventarioRepository.deleteAllInBatch();
            loteRepository.deleteAllInBatch();
            productoRepository.deleteAllInBatch();
            compradorRepository.deleteAllInBatch();
            centroAcopioRepository.deleteAllInBatch();
            tipoMovimientoRepository.deleteAllInBatch();
            estadoRepository.deleteAllInBatch();
            monedaRepository.deleteAllInBatch();
            jdbcTemplate.execute("CREATE SEQUENCE IF NOT EXISTS seq_codigo_pedido START WITH 1");

            ParMonedaEntity moneda = new ParMonedaEntity();
            moneda.setCodigo("PEN");
            moneda.setNombre("Sol");
            moneda.setActivo(true);
            moneda = monedaRepository.saveAndFlush(moneda);

            CompradorEntity comprador = new CompradorEntity();
            comprador.setNombreRazonSocial("Comprador prueba");
            comprador.setActivo(true);
            comprador = compradorRepository.saveAndFlush(comprador);
            idComprador = comprador.getIdComprador();

            CentroAcopioEntity centro = new CentroAcopioEntity();
            centro.setNombre("Centro prueba");
            centro.setActivo(true);
            centro = centroAcopioRepository.saveAndFlush(centro);
            idCentro = centro.getIdCentroAcopio();

            ProductoEntity producto = new ProductoEntity();
            producto.setNombre("Producto prueba");
            producto.setActivo(true);
            producto.setMoneda(moneda);
            producto.setPrecioReferencial(new BigDecimal("4.50"));
            producto = productoRepository.saveAndFlush(producto);
            idProducto = producto.getIdProducto();

            LoteEntity lote = new LoteEntity();
            lote.setCodigoLote("LOTE-PRUEBA");
            lote.setProducto(producto);
            lote.setCentroAcopio(centro);
            lote.setActivo(true);
            lote = loteRepository.saveAndFlush(lote);
            idLote = lote.getIdLote();

            InventarioEntity inventario = new InventarioEntity();
            inventario.setProducto(producto);
            inventario.setCentroAcopio(centro);
            inventario.setStockActual(new BigDecimal("10.00"));
            inventario = inventarioRepository.saveAndFlush(inventario);
            idInventario = inventario.getIdInventario();

            idRegistrado = guardarEstado("REGISTRADO", "Registrado", (short) 1, false);
            idTransito = guardarEstado("EN_TRANSITO", "En tránsito", (short) 2, false);
            idEntregado = guardarEstado("ENTREGADO", "Entregado", (short) 3, true);
            idCancelado = guardarEstado("CANCELADO", "Cancelado", (short) 4, true);

            guardarTipoMovimiento("SALIDA_PEDIDO", (short) -1);
            guardarTipoMovimiento("DEVOLUCION_PEDIDO", (short) 1);
        });
    }

    @Test
    void crear_descuentaStockUsaPrecioDeServidorYRegistraHistorial() {
        PedidoOperacionResponse response = service.crear(request(new BigDecimal("3.25")));

        assertEquals("PEN", response.codigoMoneda());
        assertEquals(new BigDecimal("14.63"), response.montoTotal());
        assertEquals("Registrado", response.nombreEstadoPedido());
        assertEquals(1, response.detalle().size());
        assertEquals(new BigDecimal("4.50"), response.detalle().get(0).precioUnitario());
        assertEquals(1, response.historial().size());
        assertEquals(idRegistrado, response.historial().get(0).idEstadoNuevo());

        InventarioEntity inventario = inventarioRepository.findById(idInventario).orElseThrow();
        assertEquals(new BigDecimal("6.75"), inventario.getStockActual());
        List<MovimientoInventarioEntity> movimientos = movimientoRepository
                .findByPedidoIdPedidoAndTipoMovimientoCodigo(response.idPedido(), "SALIDA_PEDIDO");
        assertEquals(1, movimientos.size());
        assertEquals(new BigDecimal("3.25"), movimientos.get(0).getCantidad());
        assertEquals(new BigDecimal("10.00"), movimientos.get(0).getStockAnterior());
        assertEquals(new BigDecimal("6.75"), movimientos.get(0).getStockPosterior());
    }

    @Test
    void crear_stockInsuficienteNoGuardaPedidoNiDescuentaInventario() {
        assertThrows(IllegalArgumentException.class,
                () -> service.crear(request(new BigDecimal("10.01"))));

        assertEquals(0, pedidoRepository.count());
        assertEquals(new BigDecimal("10.00"),
                inventarioRepository.findById(idInventario).orElseThrow().getStockActual());
    }

    @Test
    void crear_bloqueoPesimistaEvitaDobleConsumoConcurrente() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        CountDownLatch preparados = new CountDownLatch(2);
        CountDownLatch iniciar = new CountDownLatch(1);
        try {
            Future<Boolean> primero = executor.submit(() -> crearConcurrente(preparados, iniciar));
            Future<Boolean> segundo = executor.submit(() -> crearConcurrente(preparados, iniciar));

            assertTrue(preparados.await(5, TimeUnit.SECONDS));
            iniciar.countDown();

            assertNotEquals(primero.get(10, TimeUnit.SECONDS), segundo.get(10, TimeUnit.SECONDS));
            assertEquals(new BigDecimal("4.00"),
                    inventarioRepository.findById(idInventario).orElseThrow().getStockActual());
            assertEquals(1, pedidoRepository.count());
        } finally {
            iniciar.countDown();
            executor.shutdownNow();
        }
    }

    @Test
    void crear_falloAlGuardarMovimientoRevierteTodaLaOperacion() {
        TestTransaction.flagForRollback();
        TestTransaction.end();
        jdbcTemplate.execute("""
                ALTER TABLE movimiento_inventario
                ADD CONSTRAINT chk_test_pedido_movimiento
                CHECK (observacion NOT LIKE 'PEDIDO_OPERACION:SALIDA:%')
                """);

        assertThrows(RuntimeException.class, () -> service.crear(request(new BigDecimal("2.00"))));

        jdbcTemplate.execute("ALTER TABLE movimiento_inventario DROP CONSTRAINT chk_test_pedido_movimiento");
        assertEquals(0L, pedidoRepository.count());
        assertEquals(0L, detallePedidoRepository.count());
        assertEquals(0L, historialRepository.count());
        assertEquals(0L, movimientoRepository.count());
        assertEquals(new BigDecimal("10.00"),
                inventarioRepository.findById(idInventario).orElseThrow().getStockActual());
    }

    @Test
    void cancelar_reponeStockYUnaSegundaCancelacionNoGeneraOtraDevolucion() {
        PedidoOperacionResponse creado = service.crear(request(new BigDecimal("3.00")));

        PedidoOperacionResponse cancelado = service.cambiarEstado(
                creado.idPedido(), new PedidoOperacionEstadoRequest(idCancelado, "Cancelación de prueba"));

        assertEquals(new BigDecimal("10.00"),
                inventarioRepository.findById(idInventario).orElseThrow().getStockActual());
        assertEquals(2, cancelado.historial().size());
        assertEquals(1, movimientoRepository.findByPedidoIdPedidoAndTipoMovimientoCodigo(
                creado.idPedido(), "DEVOLUCION_PEDIDO").size());
        assertThrows(IllegalArgumentException.class, () -> service.cambiarEstado(
                creado.idPedido(), new PedidoOperacionEstadoRequest(idCancelado, null)));
        assertEquals(1, movimientoRepository.findByPedidoIdPedidoAndTipoMovimientoCodigo(
                creado.idPedido(), "DEVOLUCION_PEDIDO").size());
    }

    @Test
    void cambiarEstado_rechazaTransicionInvalida() {
        PedidoOperacionResponse creado = service.crear(request(new BigDecimal("1.00")));

        assertThrows(IllegalArgumentException.class, () -> service.cambiarEstado(
                creado.idPedido(), new PedidoOperacionEstadoRequest(idEntregado, null)));

        assertEquals(1, historialRepository.findByPedidoIdPedidoOrderByFechaCambioAscIdHistorialAsc(
                creado.idPedido()).size());
    }

    @Test
    void cambiarEstado_permiteTransicionesConfiguradas() {
        PedidoOperacionResponse creado = service.crear(request(new BigDecimal("1.00")));
        PedidoOperacionResponse enTransito = service.cambiarEstado(
                creado.idPedido(), new PedidoOperacionEstadoRequest(idTransito, null));
        PedidoOperacionResponse entregado = service.cambiarEstado(
                creado.idPedido(), new PedidoOperacionEstadoRequest(idEntregado, null));

        assertEquals("Entregado", entregado.nombreEstadoPedido());
        assertEquals(3, entregado.historial().size());
        assertThrows(IllegalArgumentException.class, () -> service.cambiarEstado(
                creado.idPedido(), new PedidoOperacionEstadoRequest(idCancelado, null)));
        assertEquals("En tránsito", enTransito.nombreEstadoPedido());
    }

    private Integer guardarEstado(String codigo, String nombre, short orden, boolean finalizado) {
        ParEstadoPedidoEntity estado = new ParEstadoPedidoEntity();
        estado.setCodigo(codigo);
        estado.setNombre(nombre);
        estado.setOrden(orden);
        estado.setEsFinal(finalizado);
        estado.setActivo(true);
        return estadoRepository.saveAndFlush(estado).getIdEstadoPedido();
    }

    private void guardarTipoMovimiento(String codigo, short signo) {
        ParTipoMovimientoEntity tipo = new ParTipoMovimientoEntity();
        tipo.setCodigo(codigo);
        tipo.setNombre(codigo);
        tipo.setSigno(signo);
        tipo.setActivo(true);
        tipoMovimientoRepository.saveAndFlush(tipo);
    }

    private PedidoOperacionRequest request(BigDecimal cantidad) {
        return new PedidoOperacionRequest(
                idComprador,
                idCentro,
                List.of(new PedidoOperacionDetalleRequest(idProducto, idLote, cantidad)),
                null,
                null
        );
    }

    private boolean crearConcurrente(CountDownLatch preparados, CountDownLatch iniciar) throws InterruptedException {
        preparados.countDown();
        iniciar.await();
        try {
            service.crear(request(new BigDecimal("6.00")));
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}
