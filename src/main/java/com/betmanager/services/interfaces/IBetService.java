package com.betmanager.services.interfaces;

import com.betmanager.models.entities.Bet;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IBetService {
    Bet saveBet(Bet bet, Long userId);
    List<Bet> getBetsByUserId(Long userId);
    Bet udapteBet(Long betId, Bet betDetails);
    void deleteBet(Long betId);

    List<Bet> generateReport(LocalDate startDate, LocalDate endDate, String status, BigDecimal minAmount, BigDecimal maxAmount, Long userId);
}
