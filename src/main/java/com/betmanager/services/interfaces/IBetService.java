package com.betmanager.services.interfaces;

import com.betmanager.models.entities.Bet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IBetService {
    Bet saveBet(Bet bet, Long userId);
    Page<Bet> getBetsByUserId(Long userId, Pageable pageable);
    Bet udapteBet(Long betId, Bet betDetails);
    void deleteBet(Long betId);

    Page<Bet> getBetsByFilters(LocalDate startDate, LocalDate endDate, String status, BigDecimal minAmount, BigDecimal maxAmount, Long userId, Pageable pageable);
}
