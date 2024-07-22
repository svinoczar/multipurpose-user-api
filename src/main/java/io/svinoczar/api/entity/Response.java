package io.svinoczar.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Response {
    protected String message;
    protected OffsetDateTime timeStamp;
    protected int statusCode;
    @JsonIgnore
    protected String devMessage;
    @JsonIgnore
    protected String username;
}
