package com.betmanager.services.interfaces;

import com.betmanager.models.dtos.BetAuditResponse;

import java.util.List;

public interface IBetAuditService {

    List<BetAuditResponse> getUserBetAudits(Long userId);

}
