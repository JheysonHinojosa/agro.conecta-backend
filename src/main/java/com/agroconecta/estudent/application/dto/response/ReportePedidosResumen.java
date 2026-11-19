package com.agroconecta.estudent.application.dto.response;

import java.math.BigDecimal;

public record ReportePedidosResumen(String pais, String moneda, Long cantidadPedidos, BigDecimal importe) {}
