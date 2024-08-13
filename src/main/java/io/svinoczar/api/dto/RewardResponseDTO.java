package io.svinoczar.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.svinoczar.api.entity.Response;
import io.svinoczar.api.entity.RewardReason;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;

@Data
@Builder(builderMethodName = "rewardResponseBuilder")
@EqualsAndHashCode(callSuper = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RewardResponseDTO extends Response {
    private Float value;
    private RewardReason reason;
    private String rewardedUserName;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private boolean valid;
    private boolean isVisible;

    //FIXME: Убрать поля из класса Response
    private String message; //TODO: Разобраться с билдером
    private OffsetDateTime timeStamp;
    private int statusCode;
    @JsonIgnore
    private String devMessage;
    @JsonIgnore
    private String username;
}