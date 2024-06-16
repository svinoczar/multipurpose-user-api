package io.svinoczar.api.entity;

import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToOne;
import org.springframework.data.relational.core.mapping.Table;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@Table(name = "rewards")
@Builder( toBuilder = true )
@AllArgsConstructor
@NoArgsConstructor
public class RewardEntity {
    @Id
    private Long id;
    private Float value;
    private RewardReason reason;
    private String description;
    @ManyToOne
    @JoinColumn(name = "rewarded_user_id")
    private Long rewardedUserId;
    private OffsetDateTime receivedAt;
    private boolean valid;
    private boolean isVisible;
}
