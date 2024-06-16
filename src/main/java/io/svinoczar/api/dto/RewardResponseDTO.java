package io.svinoczar.api.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.svinoczar.api.entity.Response;
import io.svinoczar.api.entity.RewardReason;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;

@Data
@Builder(builderMethodName = "rewardResponseBuilder")
@EqualsAndHashCode(callSuper = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RewardResponseDTO extends Response {
    private Float value;
    private RewardReason reason;
    private String rewardedUserName;
    private OffsetDateTime receivedAt;
    private boolean valid;
    private boolean isVisible;
}