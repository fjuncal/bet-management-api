package com.betmanager.services;

import com.betmanager.models.entities.Bet;
import com.betmanager.services.interfaces.IReportService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ReportServiceImpl implements IReportService {
    @Override
    public ResponseEntity<byte[]> exportReportAsPdf(List<Bet> bets) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            // Adicionando conteúdo ao PDF
            document.add(new Paragraph("Bet Report"));
            PdfPTable table = new PdfPTable(5); // Número de colunas
            table.addCell("ID");
            table.addCell("Type");
            table.addCell("Amount");
            table.addCell("Status");
            table.addCell("Odds");

            for (Bet bet : bets) {
                table.addCell(bet.getId().toString());
                table.addCell(bet.getType());
                table.addCell(bet.getAmount().toString());
                table.addCell(bet.getStatus());
                table.addCell(bet.getOdds().toString());
            }

            document.add(table);
            document.close();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=bet_report.pdf");
            return new ResponseEntity<>(out.toByteArray(), headers, HttpStatus.OK);
        } catch (DocumentException | IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
