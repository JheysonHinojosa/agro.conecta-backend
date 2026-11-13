package com.agroconecta.estudent.infrastructure.adapter.out.mapper;

import com.agroconecta.estudent.application.dto.request.MovimientoInventarioRequest;
import com.agroconecta.estudent.application.dto.response.MovimientoInventarioResponse;
import com.agroconecta.estudent.infrastructure.adapter.out.Entity.MovimientoInventarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovimientoInventarioMapper {

    @Mapping(target = "idMovimiento", ignore = true)
    @Mapping(target = "inventario", ignore = true)
    @Mapping(target = "tipoMovimiento", ignore = true)
    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "lote", ignore = true)
    @Mapping(target = "stockAnterior", ignore = true)
    @Mapping(target = "stockPosterior", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    MovimientoInventarioEntity toEntity(MovimientoInventarioRequest request);

    @Mapping(source = "inventario.idInventario", target = "idInventario")
    @Mapping(source = "tipoMovimiento.idTipoMovimiento", target = "idTipoMovimiento")
    @Mapping(source = "tipoMovimiento.nombre", target = "nombreTipoMovimiento")
    @Mapping(source = "pedido.idPedido", target = "idPedido")
    @Mapping(source = "lote.idLote", target = "idLote")
    @Mapping(source = "lote.codigoLote", target = "codigoLote")
    MovimientoInventarioResponse toResponse(MovimientoInventarioEntity entity);
}
