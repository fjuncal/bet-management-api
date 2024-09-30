package com.betmanager.interfaces.rest;

import com.betmanager.models.entities.Bet;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface IBetAPI {

    @PostMapping("/user/{userId}")
    ResponseEntity<Bet> createBet(@Valid @RequestBody Bet bet, @PathVariable Long userId);

    @GetMapping("/user/{userId}")
    ResponseEntity<PagedModel<EntityModel<Bet>>> getBetsByUserId(@PathVariable Long userId, Pageable pageable, PagedResourcesAssembler<Bet> assembler);

    @PutMapping("/{betId}")
    ResponseEntity<Bet> updateBet(@PathVariable Long betId, @RequestBody Bet betDetails);

    @DeleteMapping("/{betId}")
    ResponseEntity<Void> deleteBet(@PathVariable Long betId);

    @GetMapping("/getByFilters")
    ResponseEntity<PagedModel<EntityModel<Bet>>> getBetsByFilters(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) BigDecimal minAmount,
            @RequestParam(required = false) BigDecimal maxAmount,
            @RequestParam Long userId,
            Pageable pageable,
            PagedResourcesAssembler<Bet> assembler); //userId obrigatorio
}
