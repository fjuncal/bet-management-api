package com.betmanager.interfaces.rest.controllers;


import com.betmanager.models.dtos.BetAuditResponse;
import com.betmanager.services.BetAuditServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<PagedModel<EntityModel<BetAuditResponse>>> getUserBetAuditHistory(@PathVariable Long userId, Pageable pageable, PagedResourcesAssembler<BetAuditResponse> assembler) {
        Page<BetAuditResponse> auditHistory = betAuditService.getUserBetAudits(userId, pageable);
        return new ResponseEntity<>(assembler.toModel(auditHistory), HttpStatus.OK);
    }
}
