package com.agroconecta.estudent.domain.port;

import com.agroconecta.estudent.application.dto.FiltroProductorQuery;
import com.agroconecta.estudent.domain.model.Productor;

import java.util.List;
import java.util.Optional;

public interface ProductoRepositoryPort {

    Productor guardar(Productor productor, String usuario);

    Productor actualizar(Long idProductor, Productor cambios, String usuario);

    Optional<Productor> buscarPorId(Long idProductor);

    Optional<Productor> buscarPorDocumento(Integer idTipoDocumento, String nroDocumento);

    List<Productor> listar(FiltroProductorQuery filtros);

    void eliminar(Long idProductor, String usuario);
}
