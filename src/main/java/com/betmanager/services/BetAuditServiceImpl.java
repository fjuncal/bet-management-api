package com.betmanager.services;

import com.betmanager.models.dtos.BetAuditResponse;
import com.betmanager.models.entities.Bet;
import com.betmanager.services.interfaces.IBetAuditService;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BetAuditServiceImpl implements IBetAuditService {
    private final EntityManager entityManager;


    @Override
    @SuppressWarnings("unchecked")
    public Page<BetAuditResponse> getUserBetAudits(Long userId, Pageable pageable) {
        AuditReader reader = AuditReaderFactory.get(entityManager);

        // Obtenha todas as revisões de apostas relacionadas ao userId
        List<Object[]> revisions = reader.createQuery()
                .forRevisionsOfEntity(Bet.class, false, true)
                .add(AuditEntity.property("user_id").eq(userId))
                .addOrder(AuditEntity.revisionProperty("timestamp").desc())
                .getResultList();

        // Transforme os resultados em uma resposta apropriada
        List<BetAuditResponse> auditResponses = revisions.stream()
                .map(this::mapToAuditResponse)
                .toList();

        // Fazer a paginação manual dos resultados
        int start = Math.min((int) pageable.getOffset(), auditResponses.size());
        int end = Math.min((start + pageable.getPageSize()), auditResponses.size());

        return new PageImpl<>(auditResponses.subList(start, end), pageable, auditResponses.size());
    }


    private BetAuditResponse mapToAuditResponse(Object[] revision) {
        Bet bet = (Bet) revision[0];
        // A revisão seria a segunda parte do array
        DefaultRevisionEntity revEntity = (DefaultRevisionEntity) revision[1];
        // Operação (ADD, MOD, DEL) seria a terceira parte
        RevisionType revType = (RevisionType) revision[2];

        return new BetAuditResponse(
                bet.getId(),
                bet.getAmount(),
                bet.getStatus(),
                revEntity.getRevisionDate(),
                revType.name()
        );
    }
}
