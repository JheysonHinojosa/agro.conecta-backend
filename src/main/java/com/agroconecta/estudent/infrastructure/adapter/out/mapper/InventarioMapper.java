package com.agroconecta.estudent.infrastructure.adapter.out.mapper;

import com.agroconecta.estudent.application.dto.request.InventarioRequest;
import com.agroconecta.estudent.application.dto.response.InventarioResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.InventarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InventarioMapper {

    @Mapping(target = "idInventario", ignore = true)
    @Mapping(target = "centroAcopio", ignore = true)
    @Mapping(target = "producto", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    InventarioEntity toEntity(InventarioRequest request);

    @Mapping(target = "idInventario", ignore = true)
    @Mapping(target = "centroAcopio", ignore = true)
    @Mapping(target = "producto", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntity(@MappingTarget InventarioEntity entity, InventarioRequest request);

    @Mapping(source = "centroAcopio.idCentroAcopio", target = "idCentroAcopio")
    @Mapping(source = "centroAcopio.nombre", target = "nombreCentroAcopio")
    @Mapping(source = "producto.idProducto", target = "idProducto")
    @Mapping(source = "producto.nombre", target = "nombreProducto")
    @Mapping(target = "bajoMinimo", expression = "java(entity.getStockActual() != null && entity.getStockMinimo() != null && entity.getStockActual().compareTo(entity.getStockMinimo()) < 0)")
    InventarioResponse toResponse(InventarioEntity entity);
}
