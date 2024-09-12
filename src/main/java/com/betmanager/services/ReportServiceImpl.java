package com.betmanager.services;

import com.betmanager.models.entities.Bet;
import com.betmanager.services.interfaces.IReportService;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import com.opencsv.CSVWriter;
import lombok.SneakyThrows;
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

    @SneakyThrows
    public ResponseEntity<byte[]> exportReportAsPdf(Page<Bet> bets) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Adicionando o logotipo ao cabeçalho
        Image logo = new Image(ImageDataFactory.create("https://conceitos.com/wp-content/uploads/Logotipo.jpg")); // Substitua com o caminho correto do logotipo
        logo.setWidth(100); // Ajusta o tamanho do logotipo
        document.add(logo);

        // Adicionando o título
        Paragraph title = new Paragraph("Relatório de Apostas")
                .setBold()
                .setFontSize(18)
                .setMarginBottom(20);
        document.add(title);

        // Criando a tabela com bordas estilizadas
        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 2, 2, 1, 1}));
        table.setWidth(UnitValue.createPercentValue(100));

        // Cabeçalhos da tabela
        addTableHeader(table);

        // Preenchendo a tabela com os dados
        for (Bet bet : bets) {
            addRow(table, bet);
        }

        document.add(table);
        document.close();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bet_report.pdf");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

        return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);
    }

    private void addTableHeader(Table table) {
        String[] headers = {"ID", "Tipo", "Status", "Quantia", "Odds"};
        for (String header : headers) {
            Cell headerCell = new Cell().add(new Paragraph(header));
            headerCell.setBackgroundColor(ColorConstants.LIGHT_GRAY);
            headerCell.setBold();
            headerCell.setBorder(new SolidBorder(1));
            table.addHeaderCell(headerCell);
        }
    }

    private void addRow(Table table, Bet bet) {
        table.addCell(new Cell().add(new Paragraph(String.valueOf(bet.getId()))));
        table.addCell(new Cell().add(new Paragraph(bet.getType())));
        table.addCell(new Cell().add(new Paragraph(bet.getStatus())));
        table.addCell(new Cell().add(new Paragraph(String.valueOf(bet.getAmount()))));
        table.addCell(new Cell().add(new Paragraph(String.valueOf(bet.getOdds()))));
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
