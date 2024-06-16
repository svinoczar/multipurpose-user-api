package io.svinoczar.api.entity;

import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToOne;
import org.springframework.data.relational.core.mapping.Table;
import lombok.*;
import org.springframework.data.annotation.Id;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Data
@Table(name = "rewards")
@Builder( toBuilder = true )
@AllArgsConstructor
@NoArgsConstructor
public class RewardEntity {
    @Id
    private Long id;
    private Float value;
    private String rewardReason;
    private String rewardDescription;
    @ManyToOne
    @JoinColumn(name = "rewarded_user_id")
    private Long rewardedUserId;
    private LocalDateTime receivedAt;
    private boolean valid;
    private boolean isVisible;
}
