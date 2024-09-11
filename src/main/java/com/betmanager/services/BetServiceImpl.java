package com.betmanager.services;

import com.betmanager.exception.NoBetFoundException;
import com.betmanager.exception.NoUserFoundException;
import com.betmanager.models.entities.Bet;
import com.betmanager.models.entities.UserEntity;
import com.betmanager.repositories.BetRepository;
import com.betmanager.repositories.UserRepository;
import com.betmanager.repositories.specification.BetSpecifications;
import com.betmanager.services.interfaces.IBetService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class BetServiceImpl implements IBetService {
    private final BetRepository betRepository;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(BetServiceImpl.class);

    @Override
    public Bet saveBet(Bet bet, Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NoUserFoundException("User not found"));
        bet.setUser(userEntity);
        logger.info("User {} created a bet with ID {}", userId, bet.getId());
        return betRepository.save(bet);
    }

    @Override
    public List<Bet> getBetsByUserId(Long userId) {
        return betRepository.findByUserId(userId);
    }

    @Override
    public Bet udapteBet(Long betId, Bet betDetails) {
        Bet bet = betRepository.findById(betId)
                .orElseThrow(() -> new NoBetFoundException("Bet not found"));
        bet.setType(betDetails.getType());
        bet.setAmount(betDetails.getAmount());
        bet.setStatus(betDetails.getStatus());
        bet.setOdds(betDetails.getOdds());

        logger.info("User {} updated a bet with ID {}", bet.getUser().getId(), bet.getId());
        return betRepository.save(bet);
    }

    @Override
    public void deleteBet(Long betId) {
        Bet bet = betRepository.findById(betId)
                .orElseThrow(() -> new NoBetFoundException("Bet not found"));

        logger.info("User {} deleted a bet with ID {}", bet.getUser().getId(), betId);
        betRepository.delete(bet);
    }

    @Override
    public List<Bet> generateReport(LocalDate startDate, LocalDate endDate, String status, BigDecimal minAmount, BigDecimal maxAmount, Long userId) {
        return betRepository.findAll(Specification.where(
                BetSpecifications.hasDateBetween(startDate, endDate)
                        .and(BetSpecifications.hasStatus(status))
                        .and(BetSpecifications.hasAmountBetween(minAmount, maxAmount))
                        .and(BetSpecifications.hasUser(userId))
        ));
    }
}
