package com.agroconecta.estudent.infrastructure.adapter.in.web;

import com.agroconecta.estudent.application.services.PedidoOperacionService;
import com.agroconecta.estudent.config.GlobalExceptionHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PedidoOperacionControllerTest {

    @Mock
    private PedidoOperacionService pedidoOperacionService;

    private LocalValidatorFactoryBean validator;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();
        mockMvc = MockMvcBuilders.standaloneSetup(new PedidoOperacionController(pedidoOperacionService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator)
                .build();
    }

    @AfterEach
    void tearDown() {
        validator.close();
    }

    @Test
    void crear_devuelveErrores400PorCampoConClavesCamelCase() throws Exception {
        String body = """
                {
                  "idComprador": null,
                  "idCentroAcopio": null,
                  "detalle": [
                    {"idProducto": 1, "idLote": 1, "cantidad": 0}
                  ]
                }
                """;

        String response = mockMvc.perform(post("/api/pedido-operacion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(response.contains("\"idComprador\""));
        assertTrue(response.contains("\"idCentroAcopio\""));
        assertTrue(response.contains("\"detalle[0].cantidad\""));
        verifyNoInteractions(pedidoOperacionService);
    }
}
