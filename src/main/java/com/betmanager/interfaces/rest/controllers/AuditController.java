package com.betmanager.interfaces.rest.controllers;


import com.betmanager.models.dtos.BetAuditResponse;
import com.betmanager.services.BetAuditServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/audits")
@AllArgsConstructor
public class AuditController {

    private final BetAuditServiceImpl betAuditService;


    @GetMapping("/bets/user/{userId}")
    public ResponseEntity<List<BetAuditResponse>> getUserBetAuditHistory(@PathVariable Long userId) {
        List<BetAuditResponse> auditHistory = betAuditService.getUserBetAudits(userId);
        return new ResponseEntity<>(auditHistory, HttpStatus.OK);
    }
}
