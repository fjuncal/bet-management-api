package com.betmanager.controllers;


import com.betmanager.models.entities.Bet;
import com.betmanager.services.BetServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/bets")
@AllArgsConstructor
public class BetController {
    private final BetServiceImpl betService;
    @PostMapping("/user/{userId}")
    public ResponseEntity<Bet> createBet(@RequestBody Bet bet, @PathVariable Long userId) {
        Bet savedBet = betService.saveBet(bet, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBet);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Bet>> getBetsByUserId(@PathVariable Long userId) {
        List<Bet> bets = betService.getBetsByUserId(userId);
        return ResponseEntity.ok(bets);
    }

    @PutMapping("/{betId}")
    public ResponseEntity<Bet> updateBet(@PathVariable Long betId, @RequestBody Bet betDetails) {
        Bet updatedBet = betService.udapteBet(betId, betDetails);
        return ResponseEntity.ok(updatedBet);
    }

    @DeleteMapping("/{betId}")
    public ResponseEntity<Void> deleteBet(@PathVariable Long betId) {
        betService.deleteBet(betId);
        return ResponseEntity.noContent().build();
    }
}
