package io.svinoczar.api.entity;

import io.svinoczar.api.dto.UserDTO;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    private Float value;
    private RewardReason rewardReason;
    private String rewardDescription;
    @JoinColumn
    private UserEntity rewardedUserId;
    private LocalDateTime receivedAt;
    private boolean valid;
    private boolean isVisible;
}
