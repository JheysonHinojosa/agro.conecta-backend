package com.agroconecta.estudent.application.services.impl;

import com.agroconecta.estudent.application.dto.response.ReportePedidosResumen;
import com.agroconecta.estudent.application.services.ReportePedidosService;
import com.agroconecta.estudent.infrastructure.adapter.out.repository.PedidoRepository;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportePedidosServiceImpl implements ReportePedidosService {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final PedidoRepository pedidoRepository;

    @Override
    @Transactional(readOnly = true)
    public byte[] generarPdf() throws IOException {
        List<ReportePedidosResumen> rows = pedidoRepository.resumirPedidosVigentesPorPaisYMoneda();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, output);
            document.open();
            document.add(new Paragraph("AgroConecta Latam · Reporte de pedidos", titleFont()));
            document.add(new Paragraph(
                    "Fecha de generación: " + LocalDate.now(ZoneId.of("America/Lima")).format(DATE_FORMAT),
                    bodyFont()
            ));
            document.add(new Paragraph(" "));

            if (rows.isEmpty()) {
                document.add(new Paragraph("Sin pedidos vigentes", bodyFont()));
            } else {
                PdfPTable table = new PdfPTable(new float[]{3, 1.4f, 1.6f, 2});
                table.setWidthPercentage(100);
                addHeader(table, "País de origen");
                addHeader(table, "Moneda");
                addHeader(table, "Pedidos");
                addHeader(table, "Importe");
                for (ReportePedidosResumen row : rows) {
                    table.addCell(cell(row.pais()));
                    table.addCell(cell(row.moneda()));
                    table.addCell(cell(String.valueOf(row.cantidadPedidos())));
                    table.addCell(cell(formatAmount(row.importe())));
                }
                document.add(table);
            }
            document.close();
            return output.toByteArray();
        } catch (DocumentException exception) {
            throw new IOException("No se pudo generar el reporte PDF.", exception);
        } finally {
            if (document.isOpen()) {
                document.close();
            }
        }
    }

    private void addHeader(PdfPTable table, String text) {
        com.lowagie.text.pdf.PdfPCell cell = new com.lowagie.text.pdf.PdfPCell(new Phrase(text, headerFont()));
        cell.setBackgroundColor(new Color(232, 240, 232));
        table.addCell(cell);
    }

    private com.lowagie.text.pdf.PdfPCell cell(String text) {
        return new com.lowagie.text.pdf.PdfPCell(new Phrase(text == null ? "" : text, bodyFont()));
    }

    private Font titleFont() {
        return FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, new Color(32, 75, 48));
    }

    private Font headerFont() {
        return FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.BLACK);
    }

    private Font bodyFont() {
        return FontFactory.getFont(FontFactory.HELVETICA, 10, Color.BLACK);
    }

    private String formatAmount(BigDecimal amount) {
        return amount == null ? "0.00" : amount.toPlainString();
    }
}
