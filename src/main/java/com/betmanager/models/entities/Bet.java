package com.betmanager.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@Table(name = "bets")
@NoArgsConstructor
@AllArgsConstructor
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

    private LocalDateTime placedAt;

    @NotNull(message = "Status cannot be null")
    @Size(min = 3, max = 20, message = "Status must be between 3 and 20 characters")
    private String status;

    @NotNull(message = "Odds cannot be null")
    @Positive(message = "Odds must be positive")
    private Double odds;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @PrePersist
    public void prePersist() {
        this.placedAt = LocalDateTime.now();
    }
}
