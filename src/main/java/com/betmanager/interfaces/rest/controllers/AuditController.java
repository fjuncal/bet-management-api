package com.betmanager.interfaces.rest.controllers;


import com.betmanager.models.dtos.ApiResponse;
import com.betmanager.models.dtos.BetAuditResponse;
import com.betmanager.services.BetAuditServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/audits")
@AllArgsConstructor
public class AuditController {

    private final BetAuditServiceImpl betAuditService;

    @GetMapping("/bets/user/{userId}")
    public ResponseEntity<ApiResponse<PagedModel<EntityModel<BetAuditResponse>>>> getUserBetAuditHistory(@PathVariable Long userId, Pageable pageable, PagedResourcesAssembler<BetAuditResponse> assembler) {
        Page<BetAuditResponse> auditHistory = betAuditService.getUserBetAudits(userId, pageable);
        PagedModel<EntityModel<BetAuditResponse>> pagedModel = assembler.toModel(auditHistory);

        ApiResponse<PagedModel<EntityModel<BetAuditResponse>>> response = new ApiResponse<>(
                "success",
                pagedModel,
                "Bet audit history retrieved successfully"
        );

        return ResponseEntity.ok(response);
    }
}
