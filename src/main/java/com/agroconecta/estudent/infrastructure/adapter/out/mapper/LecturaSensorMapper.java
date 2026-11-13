package com.agroconecta.estudent.infrastructure.adapter.out.mapper;

import com.agroconecta.estudent.application.dto.request.LecturaSensorRequest;
import com.agroconecta.estudent.application.dto.response.LecturaSensorResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.LecturaSensorEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface LecturaSensorMapper {

    @Mapping(target = "idLectura", ignore = true)
    @Mapping(target = "sensor", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    LecturaSensorEntity toEntity(LecturaSensorRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "idLectura", ignore = true)
    @Mapping(target = "sensor", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntity(@MappingTarget LecturaSensorEntity entity, LecturaSensorRequest request);

    @Mapping(source = "sensor.idSensor", target = "idSensor")
    @Mapping(source = "sensor.codigoSensor", target = "codigoSensor")
    LecturaSensorResponse toResponse(LecturaSensorEntity entity);
}