package io.svinoczar.api.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.svinoczar.api.entity.RewardReason;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RewardDTO {
    private Float value;
    private RewardReason reason;
    private String description;
    private Long rewardedUserId;
    private LocalDateTime receivedAt;
    private boolean valid;
    private boolean isVisible;
}
