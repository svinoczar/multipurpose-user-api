package io.svinoczar.api.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;

@Data
@Table(name = "scores")
@Builder( toBuilder = true )
@AllArgsConstructor
@NoArgsConstructor
public class ScoreEntity {
    @Id
    private Long id;
    private Integer value;
    @ManyToOne
    @JoinColumn(name = "rated_user_id")
    private Long ratedUserId;
    @JoinColumn(name = "valuer_id")
    private Long valuerId;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
