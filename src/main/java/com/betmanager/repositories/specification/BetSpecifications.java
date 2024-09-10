package com.betmanager.repositories.specification;

import com.betmanager.models.entities.Bet;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BetSpecifications {

    public static Specification<Bet> hasDateBetween(LocalDate startDate, LocalDate endDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate != null && endDate != null) {
                return criteriaBuilder.between(root.get("placedAt"), startDate, endDate);
            } else {
                return null;
            }
        };
    }

    public static Specification<Bet> hasStatus(String status) {
        return (root, query, criteriaBuilder) -> {
            if (status != null) {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("status")),
                        criteriaBuilder.lower(criteriaBuilder.literal(status))
                );
            } else {
                return null;
            }
        };
    }

    public static Specification<Bet> hasAmountBetween(BigDecimal minAmount, BigDecimal maxAmount) {
        return (root, query, criteriaBuilder) -> {
            if (minAmount != null && maxAmount != null) {
                return criteriaBuilder.between(root.get("amount"), minAmount, maxAmount);
            } else {
                return null;
            }
        };
    }

    public static Specification<Bet> hasUser(Long userId) {
        return (root, query, criteriaBuilder) -> {
            if (userId != null) {
                return criteriaBuilder.equal(root.get("user").get("id"), userId);
            } else {
                return null;
            }
        };
    }
}
