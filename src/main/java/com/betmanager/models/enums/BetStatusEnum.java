package com.betmanager.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum BetStatusEnum {
    PENDING,
    WON,
    LOST,
    CANCELLED;

    @JsonCreator
    public static BetStatusEnum fromValue(String value) {
        for (BetStatusEnum status : values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        // Retorna null ou lança exceção customizada
        return null;
    }
}
