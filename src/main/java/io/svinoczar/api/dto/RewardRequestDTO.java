package io.svinoczar.api.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.svinoczar.api.entity.RewardReason;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RewardRequestDTO {
    private Float value;
    private String reason;
    private String description;
    private String rewardedUserName;
    private Long rewardedUserId;
}