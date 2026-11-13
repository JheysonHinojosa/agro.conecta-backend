package com.agroconecta.estudent.infrastructure.adapter.out.mapper;

import com.agroconecta.estudent.application.dto.request.ParRolRequest;
import com.agroconecta.estudent.application.dto.response.ParRolResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParRolEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ParRolMapper {

    @Mapping(target = "idRol", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    ParRolEntity toEntity(ParRolRequest request);

    @Mapping(target = "idRol", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntity(@MappingTarget ParRolEntity entity, ParRolRequest request);

    ParRolResponse toResponse(ParRolEntity entity);
}
