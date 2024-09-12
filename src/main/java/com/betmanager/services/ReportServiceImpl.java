package com.betmanager.services;

import com.betmanager.models.entities.Bet;
import com.betmanager.services.interfaces.IReportService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.opencsv.CSVWriter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

@Service
public class ReportServiceImpl implements IReportService {
    @Override
    public ResponseEntity<byte[]> exportReportAsPdf(Page<Bet> bets) {
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

    @Override
    public ResponseEntity<byte[]> exportReportAsCsv(List<Bet> bets) throws IOException {
        StringWriter writer = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(writer);

        // Adiciona cabeçalhos
        String[] header = {"ID", "Type", "Amount", "Status", "Odds"};
        csvWriter.writeNext(header);

        // Adiciona dados
        for (Bet bet : bets) {
            String[] data = {bet.getId().toString(), bet.getType(), bet.getAmount().toString(),
                    bet.getStatus(), bet.getOdds().toString()};
            csvWriter.writeNext(data);
        }

        csvWriter.close();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=bet_report.csv");
        return new ResponseEntity<>(writer.toString().getBytes(), headers, HttpStatus.OK);
    }
}
