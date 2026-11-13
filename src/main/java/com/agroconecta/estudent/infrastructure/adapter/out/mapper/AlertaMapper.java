package com.agroconecta.estudent.infrastructure.adapter.out.mapper;

import com.agroconecta.estudent.application.dto.request.AlertaRequest;
import com.agroconecta.estudent.application.dto.response.AlertaResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.AlertaEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AlertaMapper {

    @Mapping(target = "idAlerta", ignore = true)
    @Mapping(target = "tipoAlerta", ignore = true)
    @Mapping(target = "centroAcopio", ignore = true)
    @Mapping(target = "producto", ignore = true)
    @Mapping(target = "sensor", ignore = true)
    @Mapping(target = "lectura", ignore = true)
    @Mapping(target = "atendida", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    AlertaEntity toEntity(AlertaRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "idAlerta", ignore = true)
    @Mapping(target = "tipoAlerta", ignore = true)
    @Mapping(target = "centroAcopio", ignore = true)
    @Mapping(target = "producto", ignore = true)
    @Mapping(target = "sensor", ignore = true)
    @Mapping(target = "lectura", ignore = true)
    @Mapping(target = "atendida", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntity(@MappingTarget AlertaEntity entity, AlertaRequest request);

    @Mapping(source = "tipoAlerta.idTipoAlerta", target = "idTipoAlerta")
    @Mapping(source = "tipoAlerta.nombre", target = "nombreTipoAlerta")
    @Mapping(source = "centroAcopio.idCentroAcopio", target = "idCentroAcopio")
    @Mapping(source = "producto.idProducto", target = "idProducto")
    @Mapping(source = "sensor.idSensor", target = "idSensor")
    @Mapping(source = "lectura.idLectura", target = "idLectura")
    AlertaResponse toResponse(AlertaEntity entity);
}