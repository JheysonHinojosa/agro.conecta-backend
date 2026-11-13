package com.agroconecta.estudent.infrastructure.adapter.out.mapper;

import com.agroconecta.estudent.application.dto.request.ParUnidadMedidaRequest;
import com.agroconecta.estudent.application.dto.response.ParUnidadMedidaResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParUnidadMedidaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ParUnidadMedidaMapper {

    @Mapping(target = "idUnidadMedida", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    ParUnidadMedidaEntity toEntity(ParUnidadMedidaRequest request);

    @Mapping(target = "idUnidadMedida", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntity(@MappingTarget ParUnidadMedidaEntity entity, ParUnidadMedidaRequest request);

    ParUnidadMedidaResponse toResponse(ParUnidadMedidaEntity entity);
}
