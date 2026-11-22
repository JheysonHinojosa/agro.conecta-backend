package com.agroconecta.estudent.infrastructure.adapter.in.web;

import com.agroconecta.estudent.application.services.impl.ResenaLoteServiceImpl;
import com.agroconecta.estudent.config.GlobalExceptionHandler;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.CompradorEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.LoteEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParEstadoPedidoEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.PedidoEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.mapper.ResenaLoteMapper;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.CompradorRepository;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.DetallePedidoRepository;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.LoteRepository;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.PedidoRepository;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.ResenaLoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ResenaLoteControllerTest {

    private static final String MENSAJE_NO_ENTREGADO =
            "Solo se pueden registrar reseñas para pedidos entregados";

    @Mock private ResenaLoteRepository resenaRepository;
    @Mock private LoteRepository loteRepository;
    @Mock private CompradorRepository compradorRepository;
    @Mock private PedidoRepository pedidoRepository;
    @Mock private DetallePedidoRepository detallePedidoRepository;
    @Mock private ResenaLoteMapper resenaMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        ResenaLoteServiceImpl service = new ResenaLoteServiceImpl(
                resenaRepository,
                loteRepository,
                compradorRepository,
                pedidoRepository,
                detallePedidoRepository,
                resenaMapper
        );
        mockMvc = MockMvcBuilders.standaloneSetup(new ResenaLoteController(service))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void crear_rechazaPedidoNoEntregadoConError400() throws Exception {
        LoteEntity lote = new LoteEntity();
        lote.setIdLote(1L);
        CompradorEntity comprador = new CompradorEntity();
        comprador.setIdComprador(2L);
        ParEstadoPedidoEntity estado = new ParEstadoPedidoEntity();
        estado.setCodigo("EN_TRANSITO");
        estado.setNombre("En tránsito");
        PedidoEntity pedido = new PedidoEntity();
        pedido.setComprador(comprador);
        pedido.setEstadoPedido(estado);

        when(loteRepository.findById(1L)).thenReturn(Optional.of(lote));
        when(compradorRepository.findById(2L)).thenReturn(Optional.of(comprador));
        when(pedidoRepository.findById(3L)).thenReturn(Optional.of(pedido));

        String body = """
                {
                  "idLote": 1,
                  "idComprador": 2,
                  "idPedido": 3,
                  "puntuacion": 5,
                  "comentario": "Buena calidad"
                }
                """;

        mockMvc.perform(post("/api/resena-lote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(MENSAJE_NO_ENTREGADO))
                .andExpect(jsonPath("$.status").value(400));

        verifyNoInteractions(detallePedidoRepository, resenaRepository, resenaMapper);
    }
}
