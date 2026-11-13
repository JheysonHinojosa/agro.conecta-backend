package com.agroconecta.estudent.infrastructure.adapter.out.mapper;

import com.agroconecta.estudent.application.dto.request.ParTipoCompradorRequest;
import com.agroconecta.estudent.application.dto.response.ParTipoCompradorResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParTipoCompradorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ParTipoCompradorMapper {

    @Mapping(target = "idTipoComprador", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    ParTipoCompradorEntity toEntity(ParTipoCompradorRequest request);

    @Mapping(target = "idTipoComprador", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntity(@MappingTarget ParTipoCompradorEntity entity, ParTipoCompradorRequest request);

    ParTipoCompradorResponse toResponse(ParTipoCompradorEntity entity);
}