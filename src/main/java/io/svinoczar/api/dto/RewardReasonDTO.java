package io.svinoczar.api.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.svinoczar.api.entity.RewardReasonType;
import lombok.Builder;
import lombok.Data;


@Data
@Builder(toBuilder = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RewardReasonDTO {
    private Long id;
    private String reason;
    private String stringName;
    private String description;
    private RewardReasonType type;
    private boolean enabled;
}
