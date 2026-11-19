package com.agroconecta.estudent.application.services.impl;

import com.agroconecta.estudent.application.dto.response.SensorHistorialResponse;
import com.agroconecta.estudent.application.services.SensorHistorialService;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.LecturaSensorEntity;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.LecturaSensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class SensorHistorialServiceImpl implements SensorHistorialService {

    private final LecturaSensorRepository lecturaSensorRepository;

    @Override
    @Transactional(readOnly = true)
    public SensorHistorialResponse consultar(Long idCentroAcopio, LocalDate desde, LocalDate hasta, int page, int pageSize) {
        Page<LecturaSensorEntity> result = lecturaSensorRepository
                .findAllBySensor_CentroAcopio_IdCentroAcopioAndFechaLecturaGreaterThanEqualAndFechaLecturaLessThan(
                        idCentroAcopio,
                        desde.atStartOfDay().atOffset(ZoneOffset.UTC),
                        hasta.plusDays(1).atStartOfDay().atOffset(ZoneOffset.UTC),
                        PageRequest.of(page - 1, pageSize, Sort.by(
                                Sort.Order.desc("fechaLectura"),
                                Sort.Order.desc("idLectura")
                        ))
                );

        return new SensorHistorialResponse(
                result.getContent().stream()
                        .map(this::mapItem)
                        .toList(),
                result.getTotalElements()
        );
    }

    private SensorHistorialResponse.Item mapItem(LecturaSensorEntity lectura) {
        return new SensorHistorialResponse.Item(
                lectura.getSensor() != null ? lectura.getSensor().getCodigoSensor() : null,
                lectura.getFechaLectura(),
                lectura.getTemperaturaC(),
                lectura.getHumedadPct()
        );
    }
}
