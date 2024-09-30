package com.betmanager.services.interfaces;

import com.betmanager.models.dtos.BetAuditResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBetAuditService {

    Page<BetAuditResponse> getUserBetAudits(Long userId, Pageable pageable);

}
