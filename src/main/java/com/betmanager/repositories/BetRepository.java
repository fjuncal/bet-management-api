package com.betmanager.repositories;

import com.betmanager.models.entities.Bet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BetRepository extends JpaRepository<Bet, Long>, JpaSpecificationExecutor<Bet> {
    List<Bet> findByUserId(Long userId);
}
