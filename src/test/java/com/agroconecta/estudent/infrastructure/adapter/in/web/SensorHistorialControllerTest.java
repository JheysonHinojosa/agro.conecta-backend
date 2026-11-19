package com.agroconecta.estudent.infrastructure.adapter.in.web;

import com.agroconecta.estudent.application.dto.response.SensorHistorialResponse;
import com.agroconecta.estudent.application.services.SensorHistorialService;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class SensorHistorialControllerTest {

    @Mock
    private SensorHistorialService sensorHistorialService;

    private LocalValidatorFactoryBean validator;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();
        mockMvc = MockMvcBuilders.standaloneSetup(new SensorHistorialController(sensorHistorialService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .setValidator(validator)
                .build();
    }

    @AfterEach
    void tearDown() {
        validator.close();
    }

    @Test
    void consultar_usaValoresPredeterminadosYDevuelveCamposEnSnakeCase() throws Exception {
        SensorHistorialResponse response = new SensorHistorialResponse(List.of(
                new SensorHistorialResponse.Item(
                        "SENSOR-01",
                        OffsetDateTime.parse("2026-03-01T10:00:00Z"),
                        new BigDecimal("18.50"),
                        new BigDecimal("70.00")
                )
        ), 1);
        org.mockito.Mockito.when(sensorHistorialService.consultar(
                1L, LocalDate.parse("2026-03-01"), LocalDate.parse("2026-03-02"), 1, 25
        )).thenReturn(response);

        mockMvc.perform(get("/api/sensores/historial")
                        .param("id_centro_acopio", "1")
                        .param("desde", "2026-03-01")
                        .param("hasta", "2026-03-02")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(1))
                .andExpect(jsonPath("$.items[0].codigo_sensor").value("SENSOR-01"))
                .andExpect(jsonPath("$.items[0].fecha_lectura").value("2026-03-01T10:00:00Z"))
                .andExpect(jsonPath("$.items[0].temperatura_c").value(18.5))
                .andExpect(jsonPath("$.items[0].humedad_pct").value(70.0));

        verify(sensorHistorialService).consultar(
                1L, LocalDate.parse("2026-03-01"), LocalDate.parse("2026-03-02"), 1, 25
        );
    }

    @Test
    void consultar_rechazaParametrosInvalidosConErroresPorCampo() throws Exception {
        mockMvc.perform(get("/api/sensores/historial")
                        .param("id_centro_acopio", "1")
                        .param("desde", "2026-02-30")
                        .param("hasta", "2026-03-01")
                        .param("page", "0")
                        .param("page_size", "101"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Parámetros de consulta inválidos."))
                .andExpect(jsonPath("$.errors.desde").exists())
                .andExpect(jsonPath("$.errors.page").exists())
                .andExpect(jsonPath("$.errors.page_size").exists());

        verifyNoInteractions(sensorHistorialService);
    }

    @Test
    void consultar_rechazaRangoDeFechasInvertido() throws Exception {
        mockMvc.perform(get("/api/sensores/historial")
                        .param("id_centro_acopio", "1")
                        .param("desde", "2026-03-02")
                        .param("hasta", "2026-03-01"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.hasta").exists());

        verifyNoInteractions(sensorHistorialService);
    }
}
