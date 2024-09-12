package com.betmanager.interfaces.rest.controllers;


import com.betmanager.interfaces.rest.IReportAPI;
import com.betmanager.services.BetServiceImpl;
import com.betmanager.services.ReportServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@AllArgsConstructor
@RequestMapping("/api/reports")
public class ReportController implements IReportAPI {
    private final ReportServiceImpl reportService;
    private final BetServiceImpl betService;
    @Override
    public ResponseEntity<byte[]> getReportPdf(LocalDate startDate, LocalDate endDate, String status, BigDecimal minAmount, BigDecimal maxAmount, Long userId) {
        return null;
    }
}
