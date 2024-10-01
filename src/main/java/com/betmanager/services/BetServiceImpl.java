package com.betmanager.services;

import com.betmanager.exception.NoBetFoundException;
import com.betmanager.exception.NoUserFoundException;
import com.betmanager.models.entities.Bet;
import com.betmanager.models.entities.UserEntity;
import com.betmanager.repositories.BetRepository;
import com.betmanager.repositories.UserRepository;
import com.betmanager.repositories.specification.BetSpecifications;
import com.betmanager.services.interfaces.IBetService;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@AllArgsConstructor
public class BetServiceImpl implements IBetService {
    private final BetRepository betRepository;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(BetServiceImpl.class);

    private EntityManager entityManager;

    @Override
    @CacheEvict(value = "betsByUserCache", key = "#userId", allEntries = true)
    public Bet saveBet(Bet bet, Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NoUserFoundException("User not found"));
        bet.setUser(userEntity);
        Bet savedBet = betRepository.save(bet);
        logger.info("User {} created a bet with ID {}", userId, savedBet.getId());
        return betRepository.save(savedBet);
    }

    @Override
    @Cacheable(value = "betsByUserCache", key = "#userId + '-' + #pageable.pageNumber")
    public Page<Bet> getBetsByUserId(Long userId, Pageable pageable) {
        return betRepository.findByUserId(userId, pageable);
    }

    @Override
    @CacheEvict(value = "betsByUserCache", key = "#betId", allEntries = true)
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
    @CacheEvict(value = "betsByUserCache", key = "#betId", allEntries = true)
    public void deleteBet(Long betId) {
        Bet bet = betRepository.findById(betId)
                .orElseThrow(() -> new NoBetFoundException("Bet not found"));

        logger.info("User {} deleted a bet with ID {}", bet.getUser().getId(), betId);
        betRepository.delete(bet);
    }

    @Override
    @Cacheable(value = "betsByFiltersCache", key = "#startDate + '-' + #endDate + '-' + #status + '-' + #minAmount + '-' + #maxAmount + '-' + #userId + '-' + #pageable.pageNumber")
    public Page<Bet> getBetsByFilters(LocalDate startDate, LocalDate endDate, String status, BigDecimal minAmount, BigDecimal maxAmount, Long userId, Pageable pageable) {
        return betRepository.findAll(Specification.where(
                BetSpecifications.hasDateBetween(startDate, endDate)
                        .and(BetSpecifications.hasStatus(status))
                        .and(BetSpecifications.hasAmountBetween(minAmount, maxAmount))
                        .and(BetSpecifications.hasUser(userId))
        ), pageable);
    }
}
