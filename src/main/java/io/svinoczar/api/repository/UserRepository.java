package io.svinoczar.api.repository;

import io.svinoczar.api.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends JpaRepository<UserEntity, Long> { //todo: change Jpa repo to NIO repo (r2dbc or smth)
    Mono<UserEntity> findByUsername(String username);
}
