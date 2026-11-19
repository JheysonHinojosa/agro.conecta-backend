package com.agroconecta.estudent.infrastructure.adapter.in.web;

import com.agroconecta.estudent.application.services.ReportePedidosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReportePedidosController {

    private static final String FILE_NAME = "AgroConecta_pedidos.pdf";

    private final ReportePedidosService reportePedidosService;

    @GetMapping(value = "/pedidos.pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> descargarPedidos() throws IOException {
        byte[] pdf = reportePedidosService.generarPdf();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment().filename(FILE_NAME).build().toString())
                .body(pdf);
    }
}
