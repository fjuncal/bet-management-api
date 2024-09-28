package com.betmanager.models.dtos;


import com.betmanager.models.enums.BetStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class BetAuditResponse {
    private Long betId;
    private Double amount;
    private BetStatusEnum status;
    private Date revisionDate;
    private String revisionType;
}
