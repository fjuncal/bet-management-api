package com.betmanager.interfaces.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface IReportAPI {
    @GetMapping("/pdf")
    public ResponseEntity<byte[]> getReportPdf(@RequestParam LocalDate startDate,
                                               @RequestParam LocalDate endDate,
                                               @RequestParam String status,
                                               @RequestParam BigDecimal minAmount,
                                               @RequestParam BigDecimal maxAmount,
                                               @RequestParam Long userId) ;
}
