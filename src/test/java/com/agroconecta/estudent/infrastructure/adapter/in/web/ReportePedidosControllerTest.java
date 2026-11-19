package com.agroconecta.estudent.infrastructure.adapter.in.web;

import com.agroconecta.estudent.application.services.ReportePedidosService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ReportePedidosControllerTest {

    @Mock
    private ReportePedidosService reportePedidosService;

    @Test
    void descargarPedidos_devuelvePdfAdjuntoConNombreDeArchivo() throws Exception {
        byte[] pdf = "%PDF-1.4\ncontenido".getBytes(java.nio.charset.StandardCharsets.US_ASCII);
        when(reportePedidosService.generarPdf()).thenReturn(pdf);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new ReportePedidosController(reportePedidosService)).build();

        mockMvc.perform(get("/api/reportes/pedidos.pdf"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF))
                .andExpect(header().string("Content-Disposition", org.hamcrest.Matchers.containsString(
                        "attachment; filename=\"AgroConecta_pedidos.pdf\""
                )))
                .andExpect(content().bytes(pdf));
    }
}
