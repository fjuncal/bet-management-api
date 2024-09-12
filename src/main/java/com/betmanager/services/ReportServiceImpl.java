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
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
        table.addCell(new Cell().add(new Paragraph(bet.getStatus().name())));
        table.addCell(new Cell().add(new Paragraph(String.valueOf(bet.getAmount()))));
        table.addCell(new Cell().add(new Paragraph(String.valueOf(bet.getOdds()))));
    }

    @Override
    public ResponseEntity<byte[]> exportReportAsCsv(List<Bet> bets) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Bet Report");

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headersSheet = {"ID", "Tipo", "Status", "Quantia", "Odds"};

            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            for (int i = 0; i < headersSheet.length; i++) {
                org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
                cell.setCellValue(headersSheet[i]);
                cell.setCellStyle(headerStyle);
            }

            // Create data rows
            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);
            dataStyle.setBorderTop(BorderStyle.THIN);

            int rowNum = 1;
            for (Bet bet : bets) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(bet.getId());
                row.createCell(1).setCellValue(bet.getType());
                row.createCell(2).setCellValue(bet.getStatus().name());
                row.createCell(3).setCellValue(bet.getAmount());
                row.createCell(4).setCellValue(bet.getOdds());

                for (int i = 0; i < headersSheet.length; i++) {
                    row.getCell(i).setCellStyle(dataStyle);
                }
            }

            // Auto size columns
            for (int i = 0; i < headersSheet.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Create title
            CreationHelper createHelper = workbook.getCreationHelper();
            org.apache.poi.ss.usermodel.Cell titleCell = sheet.createRow(0).createCell(0);
            titleCell.setCellValue("Relatório de Apostas");
            titleCell.setCellStyle(headerStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headersSheet.length - 1));

            // Write to ByteArrayOutputStream
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=bet_report.xlsx");

            return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);
        }
    }
}
