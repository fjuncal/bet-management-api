package com.betmanager.interfaces.rest.controllers;


import com.betmanager.interfaces.rest.IReportAPI;
import com.betmanager.models.entities.Bet;
import com.betmanager.services.BetServiceImpl;
import com.betmanager.services.ReportServiceImpl;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/reports")
public class ReportController implements IReportAPI {
    private final ReportServiceImpl reportService;
    private final BetServiceImpl betService;
    @Override
    public ResponseEntity<byte[]> getReportPdf(LocalDate startDate, LocalDate endDate, String status, BigDecimal minAmount, BigDecimal maxAmount, Long userId, Pageable pageable) {
        Page<Bet> bets = betService.getBetsByFilters(startDate, endDate, status, minAmount, maxAmount, userId, pageable);
        return reportService.exportReportAsPdf(bets);
    }

    @SneakyThrows
    @Override
    public ResponseEntity<byte[]> getReportCsv(LocalDate startDate, LocalDate endDate, String status, BigDecimal minAmount, BigDecimal maxAmount, Long userId, Pageable pageable) {
        Page<Bet> bets = betService.getBetsByFilters(startDate, endDate, status, minAmount, maxAmount, userId, pageable);
            return reportService.exportReportAsCsv(bets.getContent()); // CSV pode exportar a página completa

    }
}
