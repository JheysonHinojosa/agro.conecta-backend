package com.agroconecta.estudent.application.services;

import java.io.IOException;

public interface ReportePedidosService {

    byte[] generarPdf() throws IOException;
}
