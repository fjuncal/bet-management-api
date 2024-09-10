package com.betmanager.interfaces.rest;

import com.betmanager.models.entities.Bet;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IBetAPI {

    @PostMapping("/user/{userId}")
    ResponseEntity<Bet> createBet(@Valid @RequestBody Bet bet, @PathVariable Long userId);

    @GetMapping("/user/{userId}")
    ResponseEntity<List<Bet>> getBetsByUserId(@PathVariable Long userId);

    @PutMapping("/{betId}")
    ResponseEntity<Bet> updateBet(@PathVariable Long betId, @RequestBody Bet betDetails);

    @DeleteMapping("/{betId}")
    ResponseEntity<Void> deleteBet(@PathVariable Long betId);

    @GetMapping("/report")
    public ResponseEntity<List<Bet>> getBetReport(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) BigDecimal minAmount,
            @RequestParam(required = false) BigDecimal maxAmount,
            @RequestParam Long userId); //userId obrigatorio
}
