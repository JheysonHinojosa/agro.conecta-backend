package com.agroconecta.estudent.infrastructure.adapter.in.web;

import com.agroconecta.estudent.application.dto.response.SensorHistorialResponse;
import com.agroconecta.estudent.application.services.SensorHistorialService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/sensores/historial")
@RequiredArgsConstructor
public class SensorHistorialController {

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 25;
    private static final int MAX_PAGE_SIZE = 100;

    private final SensorHistorialService sensorHistorialService;

    @GetMapping
    public ResponseEntity<?> consultar(
            @RequestParam(name = "id_centro_acopio", required = false) String idCentroAcopioParam,
            @RequestParam(required = false) String desde,
            @RequestParam(required = false) String hasta,
            @RequestParam(required = false) String page,
            @RequestParam(name = "page_size", required = false) String pageSize,
            HttpServletRequest request
    ) {
        Map<String, String> errors = new LinkedHashMap<>();
        Long idCentroAcopio = parseCentro(idCentroAcopioParam, errors);
        LocalDate fechaDesde = parseFecha("desde", desde, errors);
        LocalDate fechaHasta = parseFecha("hasta", hasta, errors);
        int numeroPagina = parseEntero("page", page, DEFAULT_PAGE, 1, Integer.MAX_VALUE, errors);
        int tamanoPagina = parseEntero("page_size", pageSize, DEFAULT_PAGE_SIZE, 1, MAX_PAGE_SIZE, errors);

        if (fechaDesde != null && fechaHasta != null && fechaDesde.isAfter(fechaHasta)) {
            errors.put("hasta", "La fecha final debe ser igual o posterior a la fecha inicial.");
        }

        if (!errors.isEmpty()) {
            return badRequest(errors, request);
        }

        SensorHistorialResponse response = sensorHistorialService.consultar(
                idCentroAcopio, fechaDesde, fechaHasta, numeroPagina, tamanoPagina
        );
        return ResponseEntity.ok(response);
    }

    private Long parseCentro(String value, Map<String, String> errors) {
        if (value == null || value.isBlank()) {
            errors.put("id_centro_acopio", "El centro de acopio es requerido.");
            return null;
        }
        try {
            long id = Long.parseLong(value);
            if (id < 1) {
                errors.put("id_centro_acopio", "El centro de acopio debe ser un entero positivo.");
                return null;
            }
            return id;
        } catch (NumberFormatException exception) {
            errors.put("id_centro_acopio", "El centro de acopio debe ser un entero positivo.");
            return null;
        }
    }

    private LocalDate parseFecha(String field, String value, Map<String, String> errors) {
        if (value == null || value.isBlank()) {
            errors.put(field, "La fecha es requerida y debe usar el formato YYYY-MM-DD.");
            return null;
        }
        if (!value.matches("\\d{4}-\\d{2}-\\d{2}")) {
            errors.put(field, "La fecha debe usar el formato YYYY-MM-DD y ser válida.");
            return null;
        }
        try {
            return LocalDate.parse(value);
        } catch (DateTimeException exception) {
            errors.put(field, "La fecha debe usar el formato YYYY-MM-DD y ser válida.");
            return null;
        }
    }

    private int parseEntero(String field, String value, int defaultValue, int min, int max,
                            Map<String, String> errors) {
        if (value == null) {
            return defaultValue;
        }
        try {
            int parsed = Integer.parseInt(value);
            if (parsed < min || parsed > max) {
                errors.put(field, field.equals("page_size")
                        ? "El tamaño de página debe estar entre 1 y " + MAX_PAGE_SIZE + "."
                        : "La página debe ser un entero mayor o igual a 1.");
                return defaultValue;
            }
            return parsed;
        } catch (NumberFormatException exception) {
            errors.put(field, field.equals("page_size")
                    ? "El tamaño de página debe estar entre 1 y " + MAX_PAGE_SIZE + "."
                    : "La página debe ser un entero mayor o igual a 1.");
            return defaultValue;
        }
    }

    private ResponseEntity<Map<String, Object>> badRequest(Map<String, String> errors, HttpServletRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", OffsetDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        body.put("message", "Parámetros de consulta inválidos.");
        body.put("path", request.getRequestURI());
        body.put("errors", errors);
        return ResponseEntity.badRequest().body(body);
    }
}
