package com.agroconecta.estudent.infrastructure.adapter.out.mapper;

import com.agroconecta.estudent.application.dto.request.ProductoRequest;
import com.agroconecta.estudent.application.dto.response.ProductoResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ProductoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    @Mapping(target = "idProducto", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    @Mapping(target = "unidadMedida", ignore = true)
    @Mapping(target = "moneda", ignore = true)
    @Mapping(target = "productor", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    ProductoEntity toEntity(ProductoRequest request);

    @Mapping(target = "idProducto", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    @Mapping(target = "unidadMedida", ignore = true)
    @Mapping(target = "moneda", ignore = true)
    @Mapping(target = "productor", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntity(@MappingTarget ProductoEntity entity, ProductoRequest request);

    @Mapping(source = "categoria.idCategoria", target = "idCategoria")
    @Mapping(source = "categoria.nombre", target = "nombreCategoria")
    @Mapping(source = "unidadMedida.idUnidadMedida", target = "idUnidadMedida")
    @Mapping(source = "unidadMedida.codigo", target = "codigoUnidadMedida")
    @Mapping(source = "moneda.idMoneda", target = "idMoneda")
    @Mapping(source = "moneda.codigo", target = "codigoMoneda")
    @Mapping(source = "productor.idProductor", target = "idProductor")
    @Mapping(source = "productor.nombreRazonSocial", target = "nombreProductor")
    ProductoResponse toResponse(ProductoEntity entity);
}
