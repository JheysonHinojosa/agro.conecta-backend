package com.agroconecta.estudent.infrastructure.adapter.out.mapper;

import com.agroconecta.estudent.application.dto.request.SensorIOTRequest;
import com.agroconecta.estudent.application.dto.response.SensorIOTResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.SensorIotEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SensorIOTMapper {

    @Mapping(target = "idSensor", ignore = true)
    @Mapping(target = "centroAcopio", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    SensorIotEntity toEntity(SensorIOTRequest request);

    @Mapping(target = "idSensor", ignore = true)
    @Mapping(target = "centroAcopio", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntity(@MappingTarget SensorIotEntity entity, SensorIOTRequest request);

    @Mapping(source = "centroAcopio.idCentroAcopio", target = "idCentroAcopio")
    @Mapping(source = "centroAcopio.nombre", target = "nombreCentroAcopio")
    SensorIOTResponse toResponse(SensorIotEntity entity);
}
