package com.betmanager.interfaces.rest.controllers;


import com.betmanager.exception.NoUserFoundException;
import com.betmanager.interfaces.rest.IBetAPI;
import com.betmanager.models.dtos.ApiResponse;
import com.betmanager.models.entities.Bet;
import com.betmanager.services.BetServiceImpl;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/api/bets")
@AllArgsConstructor
public class BetController implements IBetAPI {
    private final BetServiceImpl betService;

    @Override
    @RateLimiter(name = "defaultRateLimiter", fallbackMethod = "rateLimitFallback")
    public ResponseEntity<ApiResponse<Bet>> createBet(@Valid @RequestBody Bet bet, @PathVariable Long userId) {
        Bet savedBet = betService.saveBet(bet, userId);
        ApiResponse<Bet> response = new ApiResponse<>("success", savedBet, "Bet created successfully");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<ApiResponse<Bet>> rateLimitFallback(Bet bet, Long userId, Throwable t) {
        if (t instanceof RequestNotPermitted) {
            ApiResponse<Bet> response = new ApiResponse<>(
                    "error",
                    null,
                    "Too many requests - Rate limit exceeded."
            );
            return ResponseEntity.status(429).body(response);
        } else if (t instanceof NoUserFoundException) {
            throw new NoUserFoundException(t.getMessage());
        }
        ApiResponse<Bet> response = new ApiResponse<>(
                "error",
                null,
                "An unexpected error occurred."
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @Override
    @RateLimiter(name = "defaultRateLimiter", fallbackMethod = "rateLimitFallback")
    public ResponseEntity<ApiResponse<PagedModel<EntityModel<Bet>>>> getBetsByUserId(@PathVariable Long userId, Pageable pageable, PagedResourcesAssembler<Bet> assembler) {
        Page<Bet> bets = betService.getBetsByUserId(userId, pageable);
        PagedModel<EntityModel<Bet>> model = assembler.toModel(bets);

        ApiResponse<PagedModel<EntityModel<Bet>>> response = new ApiResponse<>("success", model, "Bets retrieved successfully");

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ApiResponse<PagedModel<EntityModel<Bet>>>> rateLimitFallback(Long userId, Pageable pageable, PagedResourcesAssembler<Bet> assembler, Throwable t) {
        if (t instanceof RequestNotPermitted) {
            ApiResponse<PagedModel<EntityModel<Bet>>> response = new ApiResponse<>(
                    "error",
                    null,
                    "Too many requests - Rate limit exceeded."
            );
            return ResponseEntity.status(429).body(response);
        }
        throw new RuntimeException(t);
    }

    @Override
    public ResponseEntity<ApiResponse<Bet>> updateBet(@PathVariable Long betId, @Valid @RequestBody Bet betDetails) {
        Bet updatedBet = betService.udapteBet(betId, betDetails);
        ApiResponse<Bet> response = new ApiResponse<>("success", updatedBet, "Bet updated successfully");

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ApiResponse<Bet>> deleteBet(@PathVariable Long betId) {
        betService.deleteBet(betId);
        ApiResponse<Bet> response = new ApiResponse<>("success", null, "Bet deleted successfully");

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ApiResponse<PagedModel<EntityModel<Bet>>>> getBetsByFilters(LocalDate startDate, LocalDate endDate, String status, BigDecimal minAmount, BigDecimal maxAmount, Long userId, Pageable pageable, PagedResourcesAssembler<Bet> assembler) {
        Page<Bet> report = betService.getBetsByFilters(startDate, endDate, status, minAmount, maxAmount, userId, pageable);
        PagedModel<EntityModel<Bet>> pagedModel = assembler.toModel(report);

        ApiResponse<PagedModel<EntityModel<Bet>>> response = new ApiResponse<>(
                "success",
                pagedModel,
                "Filtered bets retrieved successfully"
        );

        return ResponseEntity.ok(response);
    }


}
