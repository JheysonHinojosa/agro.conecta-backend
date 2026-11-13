package com.agroconecta.estudent.infrastructure.adapter.out.mapper;

import com.agroconecta.estudent.application.dto.request.LoteRequest;
import com.agroconecta.estudent.application.dto.response.LoteResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.LoteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LoteMapper {

    @Mapping(target = "idLote", ignore = true)
    @Mapping(target = "producto", ignore = true)
    @Mapping(target = "centroAcopio", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    LoteEntity toEntity(LoteRequest request);

    @Mapping(target = "idLote", ignore = true)
    @Mapping(target = "producto", ignore = true)
    @Mapping(target = "centroAcopio", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntity(@MappingTarget LoteEntity entity, LoteRequest request);

    @Mapping(source = "producto.idProducto", target = "idProducto")
    @Mapping(source = "producto.nombre", target = "nombreProducto")
    @Mapping(source = "centroAcopio.idCentroAcopio", target = "idCentroAcopio")
    @Mapping(source = "centroAcopio.nombre", target = "nombreCentroAcopio")
    LoteResponse toResponse(LoteEntity entity);
}
