package com.agroconecta.estudent.infrastructure.adapter.out.mapper;

import com.agroconecta.estudent.application.dto.request.ResenaLoteRequest;
import com.agroconecta.estudent.application.dto.request.ResenaLoteUpdateRequest;
import com.agroconecta.estudent.application.dto.response.ResenaLoteResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.ResenaLoteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ResenaLoteMapper {

    @Mapping(target = "idResena", ignore = true)
    @Mapping(target = "lote", ignore = true)
    @Mapping(target = "comprador", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "fechaResena", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    ResenaLoteEntity toEntity(ResenaLoteRequest request);

    @Mapping(target = "idResena", ignore = true)
    @Mapping(target = "lote", ignore = true)
    @Mapping(target = "comprador", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "fechaResena", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntity(@MappingTarget ResenaLoteEntity entity, ResenaLoteUpdateRequest request);

    @Mapping(source = "lote.idLote", target = "idLote")
    @Mapping(source = "lote.codigoLote", target = "codigoLote")
    @Mapping(source = "comprador.idComprador", target = "idComprador")
    @Mapping(source = "comprador.nombreRazonSocial", target = "nombreComprador")
    @Mapping(source = "pedido.idPedido", target = "idPedido")
    @Mapping(source = "pedido.codigoPedido", target = "codigoPedido")
    ResenaLoteResponse toResponse(ResenaLoteEntity entity);
}
