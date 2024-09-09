package com.betmanager.services.interfaces;

import com.betmanager.models.entities.Bet;

import java.util.List;

public interface IBetService {
    Bet saveBet(Bet bet, Long userId);
    List<Bet> getBetsByUserId(Long userId);
    Bet udapteBet(Long betId, Bet betDetails);
    void deleteBet(Long betId);
}
