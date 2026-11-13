package com.agroconecta.estudent.application.services;

import com.agroconecta.estudent.application.dto.request.ProductoRequest;
import com.agroconecta.estudent.application.dto.response.ProductoResponse;

import java.util.List;

public interface ProductoService {

    ProductoResponse crear(ProductoRequest request);

    List<ProductoResponse> listar();

    ProductoResponse buscarPorId(Long id);

    ProductoResponse actualizar(Long id, ProductoRequest request);

    void eliminar(Long id);
}