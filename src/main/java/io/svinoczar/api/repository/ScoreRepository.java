package io.svinoczar.api.repository;

import io.svinoczar.api.entity.ScoreEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface ScoreRepository extends R2dbcRepository<ScoreEntity, Long> {
}
