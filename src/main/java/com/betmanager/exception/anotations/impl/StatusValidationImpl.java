package com.betmanager.exception.anotations.impl;

import com.betmanager.exception.anotations.StatusValidation;
import com.betmanager.models.enums.BetStatusEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StatusValidationImpl implements ConstraintValidator<StatusValidation, BetStatusEnum> {
    @Override
    public boolean isValid(BetStatusEnum value, ConstraintValidatorContext context) {

        // Verifica se o valor não é nulo ou se é um valor válido do enum
        if (value == null) {
            return false; // ou true, dependendo da sua regra de negócio
        }
        return value == BetStatusEnum.PENDING || value == BetStatusEnum.WON || value == BetStatusEnum.LOST || value == BetStatusEnum.CANCELLED;
    }
}
