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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BetAuditServiceImpl implements IBetAuditService {
    private final EntityManager entityManager;


    @Override
    @SuppressWarnings("unchecked")
    public List<BetAuditResponse> getUserBetAudits(Long userId) {
        AuditReader reader = AuditReaderFactory.get(entityManager);

        // Obtenha todas as revisões de apostas relacionadas ao userId
        List<Object[]> revisions = reader.createQuery()
                .forRevisionsOfEntity(Bet.class, false, true)
                .add(AuditEntity.property("user_id").eq(userId))
                .getResultList();

        // Transforme os resultados em uma resposta apropriada
        return revisions.stream()
                .map(this::mapToAuditResponse)
                .collect(Collectors.toList());
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
