package com.agroconecta.estudent.infrastructure.adapter.out.repository;

import com.agroconecta.estudent.application.services.impl.ReportePedidosServiceImpl;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.CentroAcopioEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParEstadoPedidoEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParMonedaEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParPaisEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.PedidoEntity;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.parser.PdfTextExtractor;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:orders-pdf;DB_CLOSE_DELAY=-1",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.flyway.enabled=false"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReportePedidosPdfRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Test
    void pdf_resumeEnBaseDeDatosPorPaisYMonedaYExcluyeCancelados() throws Exception {
        ParPaisEntity peru = crearPais("Perú");
        ParPaisEntity bolivia = crearPais("Bolivia");
        CentroAcopioEntity centro = crearCentro(peru);
        CentroAcopioEntity centroBolivia = crearCentro(bolivia);
        ParMonedaEntity pen = crearMoneda("PEN");
        ParMonedaEntity usd = crearMoneda("USD");
        ParMonedaEntity bob = crearMoneda("BOB");
        ParEstadoPedidoEntity registrado = crearEstado("REGISTRADO", "Registrado");
        ParEstadoPedidoEntity cancelado = crearEstado("CANCELADO", "Cancelado");
        crearPedido(centro, pen, registrado, "100.25");
        crearPedido(centro, pen, registrado, "50.75");
        crearPedido(centro, usd, registrado, "20.00");
        crearPedido(centro, usd, cancelado, "999.00");
        crearPedido(centroBolivia, bob, registrado, "7.50");
        entityManager.flush();
        entityManager.clear();

        var resumen = pedidoRepository.resumirPedidosVigentesPorPaisYMoneda();

        assertEquals(3, resumen.size());
        assertEquals("Bolivia", resumen.get(0).pais());
        assertEquals("BOB", resumen.get(0).moneda());
        assertEquals(1L, resumen.get(0).cantidadPedidos());
        assertEquals(new BigDecimal("7.50"), resumen.get(0).importe());
        assertEquals("Perú", resumen.get(1).pais());
        assertEquals("PEN", resumen.get(1).moneda());
        assertEquals(2L, resumen.get(1).cantidadPedidos());
        assertEquals(new BigDecimal("151.00"), resumen.get(1).importe());
        assertEquals("USD", resumen.get(2).moneda());
        assertEquals(1L, resumen.get(2).cantidadPedidos());
        assertEquals(new BigDecimal("20.00"), resumen.get(2).importe());

        byte[] pdf = new ReportePedidosServiceImpl(pedidoRepository).generarPdf();
        assertTrue(new String(pdf, 0, 4, java.nio.charset.StandardCharsets.US_ASCII).equals("%PDF"));
        String text = extractText(pdf);
        assertTrue(text.contains("Perú"));
        assertTrue(text.contains("Bolivia"));
        assertTrue(text.contains("BOB"));
        assertTrue(text.contains("PEN"));
        assertTrue(text.contains("USD"));
        assertTrue(text.contains("7.50"));
        assertTrue(text.contains("151.00"));
        assertTrue(text.contains("20.00"));
        assertFalse(text.contains("999.00"));
        assertTrue(text.contains("Fecha de generación:"));
    }

    @Test
    void pdfSinPedidosVigentesIncluyeMensajeSolicitado() throws Exception {
        byte[] pdf = new ReportePedidosServiceImpl(pedidoRepository).generarPdf();

        assertEquals("%PDF", new String(pdf, 0, 4, java.nio.charset.StandardCharsets.US_ASCII));
        assertTrue(extractText(pdf).contains("Sin pedidos vigentes"));
    }

    private String extractText(byte[] pdf) throws IOException {
        try (PdfReader reader = new PdfReader(pdf)) {
            PdfTextExtractor extractor = new PdfTextExtractor(reader);
            List<String> pages = new java.util.ArrayList<>();
            for (int page = 1; page <= reader.getNumberOfPages(); page++) {
                pages.add(extractor.getTextFromPage(page));
            }
            return String.join("\n", pages);
        }
    }

    private ParPaisEntity crearPais(String nombre) {
        ParPaisEntity pais = new ParPaisEntity();
        pais.setNombre(nombre);
        pais.setCodigoIso("PE");
        pais.setActivo(true);
        entityManager.persist(pais);
        return pais;
    }

    private CentroAcopioEntity crearCentro(ParPaisEntity pais) {
        CentroAcopioEntity centro = new CentroAcopioEntity();
        centro.setNombre("Acopio Cusco");
        centro.setCodigo("CUSCO");
        centro.setPais(pais);
        centro.setActivo(true);
        entityManager.persist(centro);
        return centro;
    }

    private ParMonedaEntity crearMoneda(String codigo) {
        ParMonedaEntity moneda = new ParMonedaEntity();
        moneda.setCodigo(codigo);
        moneda.setNombre(codigo);
        moneda.setActivo(true);
        entityManager.persist(moneda);
        return moneda;
    }

    private ParEstadoPedidoEntity crearEstado(String codigo, String nombre) {
        ParEstadoPedidoEntity estado = new ParEstadoPedidoEntity();
        estado.setCodigo(codigo);
        estado.setNombre(nombre);
        estado.setActivo(true);
        entityManager.persist(estado);
        return estado;
    }

    private void crearPedido(
            CentroAcopioEntity centro,
            ParMonedaEntity moneda,
            ParEstadoPedidoEntity estado,
            String importe
    ) {
        PedidoEntity pedido = new PedidoEntity();
        pedido.setCodigoPedido("PED-" + System.nanoTime());
        pedido.setCentroAcopio(centro);
        pedido.setMoneda(moneda);
        pedido.setEstadoPedido(estado);
        pedido.setFechaPedido(OffsetDateTime.now());
        pedido.setMontoTotal(new BigDecimal(importe));
        entityManager.persist(pedido);
    }
}
