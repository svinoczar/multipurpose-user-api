package io.svinoczar.api.repository;

import io.svinoczar.api.dto.UserDTO;
import io.svinoczar.api.entity.RewardEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface RewardRepository extends R2dbcRepository<RewardEntity, Long> {
        Mono<RewardEntity> findByUserId(UserDTO userId);
}
