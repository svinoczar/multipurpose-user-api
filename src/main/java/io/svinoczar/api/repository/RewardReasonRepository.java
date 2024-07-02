package io.svinoczar.api.repository;

import io.svinoczar.api.entity.RewardReasonEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface RewardReasonRepository extends R2dbcRepository<RewardReasonEntity, Long> {
}
