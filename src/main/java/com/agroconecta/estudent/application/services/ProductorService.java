package com.agroconecta.estudent.application.services;

import com.agroconecta.estudent.application.dto.request.ProductorRequest;
import com.agroconecta.estudent.application.dto.response.ProductorResponse;

import java.util.List;

public interface ProductorService {

    ProductorResponse crear(ProductorRequest request);

    List<ProductorResponse> listar();

    ProductorResponse buscarPorId(Long id);

    ProductorResponse actualizar(Long id, ProductorRequest request);

    void eliminar(Long id);
}