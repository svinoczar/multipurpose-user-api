package io.svinoczar.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.svinoczar.api.entity.UserRole;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@Builder(toBuilder = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserDTO {
    private Long id;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private UserRole userRole;

    private Float xp;
    private Integer level;
    private Float score;
    private Integer scoresCount;

    private String firstName;
    private String lastName;
    private String email;

    private boolean enabled;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
