package io.svinoczar.api.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.svinoczar.api.entity.RewardReason;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RewardDTO {
    private Long id;
    private Integer value; //can be negative
    private RewardReason rewardReason;
    private boolean isVisible;
    private LocalDateTime claimedAt; //todo: rename
}
