package io.svinoczar.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@Table("reward_reasons")
@Builder( toBuilder = true )
public class RewardReasonEntity {
    @Id
    private Long id;
    @NonNull
    private String reason;
    private String stringName;
    private String description;
    private RewardReasonType type;
    private boolean enabled;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}