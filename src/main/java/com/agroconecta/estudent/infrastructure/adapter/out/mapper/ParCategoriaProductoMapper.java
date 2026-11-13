package com.agroconecta.estudent.infrastructure.adapter.out.mapper;

import com.agroconecta.estudent.application.dto.request.ParCategoriaProductoRequest;
import com.agroconecta.estudent.application.dto.response.ParCategoriaProductoResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ParCategoriaProductoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ParCategoriaProductoMapper {

    @Mapping(target = "idCategoria", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    ParCategoriaProductoEntity toEntity(ParCategoriaProductoRequest request);

    @Mapping(target = "idCategoria", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntity(@MappingTarget ParCategoriaProductoEntity entity, ParCategoriaProductoRequest request);

    ParCategoriaProductoResponse toResponse(ParCategoriaProductoEntity entity);
}
