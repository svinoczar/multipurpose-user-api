package io.svinoczar.api.entity;

import io.svinoczar.api.dto.UserDTO;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@Table(name = "rewards")
@Builder( toBuilder = true )
@AllArgsConstructor
@NoArgsConstructor
public class RewardEntity {
    @Id
    private Long id;
    private Integer value; //can be negative
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDTO userId;
    private RewardReason rewardReason;
    private String rewardDescription;
    private boolean isVisible;
    private LocalDateTime claimedAt; //todo: rename
}
