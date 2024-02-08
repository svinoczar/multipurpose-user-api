package io.svinoczar.api.entity;

import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@Table(name = "reward")
@Builder( toBuilder = true )
@AllArgsConstructor
@NoArgsConstructor
public class RewardEntity {
    @Id
    private Long id;
    private Integer value; //can be negative
    private RewardReason rewardReason;
    private String rewardDescription;
    private boolean isVisible;
    private LocalDateTime claimedAt; //todo: rename
}
