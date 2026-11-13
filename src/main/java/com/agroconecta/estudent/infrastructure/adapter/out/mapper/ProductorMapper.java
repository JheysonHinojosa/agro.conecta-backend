package com.agroconecta.estudent.infrastructure.adapter.out.mapper;

import com.agroconecta.estudent.application.dto.request.ProductorRequest;
import com.agroconecta.estudent.application.dto.response.ProductorResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ProductorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductorMapper {

    @Mapping(target = "idProductor", ignore = true)
    @Mapping(target = "centroAcopio", ignore = true)
    @Mapping(target = "tipoDocumento", ignore = true)
    @Mapping(target = "pais", ignore = true)
    @Mapping(target = "categoriaPrincipal", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    ProductorEntity toEntity(ProductorRequest request);

    @Mapping(target = "idProductor", ignore = true)
    @Mapping(target = "centroAcopio", ignore = true)
    @Mapping(target = "tipoDocumento", ignore = true)
    @Mapping(target = "pais", ignore = true)
    @Mapping(target = "categoriaPrincipal", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntity(@MappingTarget ProductorEntity entity, ProductorRequest request);

    @Mapping(source = "centroAcopio.idCentroAcopio", target = "idCentroAcopio")
    @Mapping(source = "centroAcopio.nombre", target = "nombreCentroAcopio")
    @Mapping(source = "tipoDocumento.idTipoDocumento", target = "idTipoDocumento")
    @Mapping(source = "tipoDocumento.nombre", target = "nombreTipoDocumento")
    @Mapping(source = "pais.idPais", target = "idPais")
    @Mapping(source = "pais.nombre", target = "nombrePais")
    @Mapping(source = "categoriaPrincipal.idCategoria", target = "idCategoriaPrincipal")
    @Mapping(source = "categoriaPrincipal.nombre", target = "nombreCategoriaPrincipal")
    ProductorResponse toResponse(ProductorEntity entity);
}
