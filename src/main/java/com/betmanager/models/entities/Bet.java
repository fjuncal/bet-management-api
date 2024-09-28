package com.betmanager.models.entities;

import com.betmanager.exception.anotations.StatusValidation;
import com.betmanager.models.enums.BetStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@Table(name = "bets")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Audited
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Type cannot be null")
    @Size(min = 3, max = 100, message = "Type must be between 3 and 100 characters")
    private String type;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private Double amount;

    @CreatedDate
    private LocalDateTime placedAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    //@NotNull(message = "Status cannot be null")
    @Enumerated(EnumType.STRING)
    @StatusValidation
    private BetStatusEnum status;

    @NotNull(message = "Odds cannot be null")
    @Positive(message = "Odds must be positive")
    private Double odds;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserEntity user;

}
