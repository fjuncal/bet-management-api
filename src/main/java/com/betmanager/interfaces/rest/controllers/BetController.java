package com.betmanager.interfaces.rest.controllers;


import com.betmanager.interfaces.rest.IBetAPI;
import com.betmanager.models.entities.Bet;
import com.betmanager.services.BetServiceImpl;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/bets")
@AllArgsConstructor
public class BetController implements IBetAPI {
    private final BetServiceImpl betService;
    @Override
    public ResponseEntity<Bet> createBet(@Valid @RequestBody Bet bet, @PathVariable Long userId) {
        Bet savedBet = betService.saveBet(bet, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBet);
    }

    @Override
    @RateLimiter(name = "defaultRateLimiter", fallbackMethod = "rateLimitFallback")
    public ResponseEntity<List<Bet>> getBetsByUserId(@PathVariable Long userId) {
        List<Bet> bets = betService.getBetsByUserId(userId);
        return ResponseEntity.ok(bets);
    }

    @Override
    public ResponseEntity<Bet> updateBet(@PathVariable Long betId, @RequestBody Bet betDetails) {
        Bet updatedBet = betService.udapteBet(betId, betDetails);
        return ResponseEntity.ok(updatedBet);
    }

    @Override
    public ResponseEntity<Void> deleteBet(@PathVariable Long betId) {
        betService.deleteBet(betId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<Bet>> getBetReport(LocalDate startDate, LocalDate endDate, String status, BigDecimal minAmount, BigDecimal maxAmount, Long userId) {
        List<Bet> report = betService.generateReport(startDate, endDate, status, minAmount, maxAmount, userId);
        return ResponseEntity.ok(report);
    }

    public ResponseEntity<List<Bet>> rateLimitFallback(Long userId, Throwable t) {
        return ResponseEntity.status(429).body(Collections.emptyList());
    }
}
