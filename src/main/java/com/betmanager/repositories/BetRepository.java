package com.betmanager.repositories;

import com.betmanager.models.entities.Bet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BetRepository extends JpaRepository<Bet, Long>, JpaSpecificationExecutor<Bet> {
    Page<Bet> findByUserId(Long userId, Pageable pageable);
    Page<Bet> findAll(Specification<Bet> spec, Pageable pageable);

}
