package com.agroconecta.estudent.infrastructure.adapter.in.web;

import com.agroconecta.estudent.application.services.ProductoService;
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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ProductoControllerTest {

    @Mock
    private ProductoService productoService;

    private LocalValidatorFactoryBean validator;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();
        mockMvc = MockMvcBuilders.standaloneSetup(new ProductoController(productoService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator)
                .build();
    }

    @AfterEach
    void tearDown() {
        validator.close();
    }

    @Test
    void crear_rechazaPrecioReferencialCeroConError400() throws Exception {
        String body = """
                {
                  "codigo": "PR-TEST",
                  "nombre": "Producto de prueba",
                  "idCategoria": 1,
                  "idUnidadMedida": 1,
                  "precioReferencial": 0,
                  "idMoneda": 1,
                  "idProductor": 1,
                  "activo": true
                }
                """;

        String response = mockMvc.perform(post("/api/producto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.precioReferencial")
                        .value("El precio referencial debe ser mayor a cero"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(response.contains("\"status\":400"));
        verifyNoInteractions(productoService);
    }
}
