package com.agroconecta.estudent.infrastructure.adapter.out.mapper;

import com.agroconecta.estudent.application.dto.request.ParMonedaRequest;
import com.agroconecta.estudent.application.dto.response.ParMonedaResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParMonedaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ParMonedaMapper {

    @Mapping(target = "idMoneda", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    ParMonedaEntity toEntity(ParMonedaRequest request);

    @Mapping(target = "idMoneda", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntity(@MappingTarget ParMonedaEntity entity, ParMonedaRequest request);

    ParMonedaResponse toResponse(ParMonedaEntity entity);
}
